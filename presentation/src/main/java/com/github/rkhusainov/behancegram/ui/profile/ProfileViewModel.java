package com.github.rkhusainov.behancegram.ui.profile;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.rkhusainov.behancegram.utils.DateUtils;
import com.github.rkhusainov.domain.model.user.User;
import com.github.rkhusainov.domain.service.ProfileService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel {

    @Inject
    ProfileService mService;

    private Disposable mDisposable;
    private String mUsername;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadProfile();
        }
    };

    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private ObservableBoolean isErrorVisible = new ObservableBoolean(false);
    private ObservableField<String> mDisplayName = new ObservableField<>();
    private ObservableField<String> mProfileCreatedOn = new ObservableField<>();
    private ObservableField<String> mProfileLocation = new ObservableField<>();
    private ObservableField<String> mProfileImageUrl = new ObservableField<>();

    @Inject
    public ProfileViewModel() {
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    void loadProfile() {
        mDisposable = mService.getUser(mUsername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> isLoading.set(true))
                .doFinally(() -> isLoading.set(false))
                .subscribe(userResponse -> {
                    isErrorVisible.set(false);
                    bindProfileFields(userResponse);
                }, throwable -> isErrorVisible.set(true));
    }

    private void bindProfileFields(User user) {
        mDisplayName.set(user.getDisplayName());
        mProfileCreatedOn.set(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.set(user.getLocation());
        mProfileImageUrl.set(user.getImage().getPhotoUrl());
    }

    public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
        return mRefreshListener;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public ObservableBoolean getIsErrorVisible() {
        return isErrorVisible;
    }

    public ObservableField<String> getDisplayName() {
        return mDisplayName;
    }

    public ObservableField<String> getProfileCreatedOn() {
        return mProfileCreatedOn;
    }

    public ObservableField<String> getProfileLocation() {
        return mProfileLocation;
    }

    public ObservableField<String> getProfileImageUrl() {
        return mProfileImageUrl;
    }

    public void onDispatchDetach() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
