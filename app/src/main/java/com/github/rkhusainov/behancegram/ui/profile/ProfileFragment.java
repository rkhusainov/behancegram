package com.github.rkhusainov.behancegram.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.rkhusainov.behancegram.R;
import com.github.rkhusainov.behancegram.common.RefreshOwner;
import com.github.rkhusainov.behancegram.common.Refreshable;
import com.github.rkhusainov.behancegram.data.Storage;
import com.github.rkhusainov.behancegram.data.model.user.User;
import com.github.rkhusainov.behancegram.utils.ApiUtils;
import com.github.rkhusainov.behancegram.utils.DateUtils;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileFragment extends Fragment implements Refreshable {

    public static final String PROFILE_KEY = "PROFILE_KEY";

    private Storage mStorage;
    private RefreshOwner mRefreshOwner;
    private View mErrorView;
    private View mProfileView;
    private String mUsername;
    private Disposable mDisposable;

    private ImageView mProfileImage;
    private TextView mProfileName;
    private TextView mProfileCreatedOn;
    private TextView mProfileLocation;

    public static ProfileFragment newInstance(Bundle args) {

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Storage.StorageOwner) {
            mStorage = ((Storage.StorageOwner) context).obtainStorage();
        }

        if (context instanceof RefreshOwner) {
            mRefreshOwner = (RefreshOwner) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mErrorView = view.findViewById(R.id.errorView);
        mProfileView = view.findViewById(R.id.view_profile);

        mProfileImage = view.findViewById(R.id.iv_profile);
        mProfileName = view.findViewById(R.id.tv_display_name_details);
        mProfileCreatedOn = view.findViewById(R.id.tv_created_on_details);
        mProfileLocation = view.findViewById(R.id.tv_location_details);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments() != null) {
            mUsername = getArguments().getString(PROFILE_KEY);
        }

        if (getActivity() != null) {
            getActivity().setTitle(mUsername);
        }

        mProfileView.setVisibility(View.VISIBLE);

        getProfile();

    }

    @Override
    public void onRefreshData() {
        getProfile();
    }

    void getProfile() {
        mDisposable = ApiUtils.getApi().getUserInfo(mUsername)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getUser(mUsername) : null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mRefreshOwner.setRefreshState(true))
                .doFinally(() -> mRefreshOwner.setRefreshState(false))
                .subscribe(userResponse -> {
                    mErrorView.setVisibility(View.GONE);
                    mProfileView.setVisibility(View.VISIBLE);
                    bind(userResponse.getUser());
                }, throwable -> {
                    mErrorView.setVisibility(View.VISIBLE);
                    mProfileView.setVisibility(View.GONE);
                });
    }

    private void bind(User user) {
        Picasso.get().load(user.getImage().getPhotoUrl())
                .fit()
                .into(mProfileImage);

        mProfileName.setText(user.getDisplayName());
        mProfileCreatedOn.setText(DateUtils.format(user.getCreatedOn()));
        mProfileLocation.setText(user.getLocation());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
