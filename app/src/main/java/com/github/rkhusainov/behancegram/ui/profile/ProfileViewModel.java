package com.github.rkhusainov.behancegram.ui.profile;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.rkhusainov.behancegram.data.Storage;
import com.github.rkhusainov.behancegram.data.model.user.User;
import com.github.rkhusainov.behancegram.utils.ApiUtils;
import com.github.rkhusainov.behancegram.utils.DateUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileViewModel {

    private Disposable mDisposable;
    private Storage mStorage;
    private String mUsername;
    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getProfile();
        }
    };

    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private ObservableBoolean isErrorVisible = new ObservableBoolean(false);
    private ObservableField<String> mDisplayName = new ObservableField<>();
    private ObservableField<String> mProfileCreatedOn = new ObservableField<>();
    private ObservableField<String> mProfileLocation = new ObservableField<>();
    private ObservableField<String> mProfileImageUrl = new ObservableField<>();


    public ProfileViewModel(Storage storage, String username) {
        mStorage = storage;
        mUsername = username;
    }

    void getProfile() {
        mDisposable = ApiUtils.getApi().getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getUser(mUsername) : null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> isLoading.set(true))
                .doFinally(() -> isLoading.set(false))
                .subscribe(userResponse -> {
                    isErrorVisible.set(false);
                    bindProfileFields(userResponse.getUser());
                }, throwable -> {
                    isErrorVisible.set(true);
                });
    }

    private void bindProfileFields(User user) {
        mDisplayName.set(user.getDisplayName());
        mProfileCreatedOn.set(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.set(user.getLocation());
        mProfileImageUrl.set(user.getImage().getPhotoUrl());
    }

    public void onDispatchDetach() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
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
}
