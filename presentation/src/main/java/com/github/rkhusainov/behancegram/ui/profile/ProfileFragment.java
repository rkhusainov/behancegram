package com.github.rkhusainov.behancegram.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.rkhusainov.behancegram.AppDelegate;
import com.github.rkhusainov.behancegram.databinding.ProfileBinding;

import javax.inject.Inject;

import toothpick.Toothpick;

public class ProfileFragment extends Fragment {

    public static final String PROFILE_KEY = "PROFILE_KEY";

    private String mUsername;
    private ProfileBinding mProfileBinding;
    @Inject
    ProfileViewModel mProfileViewModel;


    public static ProfileFragment newInstance(Bundle args) {

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProfileBinding = ProfileBinding.inflate(inflater, container, false);
        return mProfileBinding.getRoot();
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

        Toothpick.inject(this, AppDelegate.getAppScope());
        mProfileViewModel.setUsername(mUsername);
        mProfileBinding.setVm(mProfileViewModel);
        mProfileViewModel.loadProfile();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mProfileViewModel.onDispatchDetach();
    }
}
