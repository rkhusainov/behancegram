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
import com.github.rkhusainov.behancegram.databinding.ProfileBinding;
import com.github.rkhusainov.behancegram.utils.ApiUtils;
import com.github.rkhusainov.behancegram.utils.DateUtils;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProfileFragment extends Fragment {

    public static final String PROFILE_KEY = "PROFILE_KEY";

    private String mUsername;
    private Storage mStorage;
    private ProfileViewModel mProfileViewModel;
    private ProfileBinding mProfileBinding;

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
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProfileBinding = ProfileBinding.inflate(inflater, container, false);
        return mProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        mProfileViewModel = new ProfileViewModel(mStorage, mUsername);
        mProfileBinding.setVm(mProfileViewModel);

        mProfileViewModel.getProfile();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mProfileViewModel.onDispatchDetach();
    }
}
