package com.github.rkhusainov.behancegram.ui.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.rkhusainov.behancegram.AppDelegate;
import com.github.rkhusainov.behancegram.R;
import com.github.rkhusainov.behancegram.databinding.ProjectsBinding;
import com.github.rkhusainov.behancegram.ui.profile.ProfileActivity;

import javax.inject.Inject;

import toothpick.Toothpick;

import static com.github.rkhusainov.behancegram.ui.profile.ProfileActivity.USERNAME_KEY;
import static com.github.rkhusainov.behancegram.ui.profile.ProfileFragment.PROFILE_KEY;

public class ProjectsFragment extends Fragment {

    @Inject
    ProjectsViewModel mProjectsViewModel;
    private ProjectsBinding mBinding;

    private ProjectsAdapter.OnItemClickListener mOnItemClickListener = new ProjectsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String userName) {
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            Bundle args = new Bundle();
            args.putString(PROFILE_KEY, userName);
            intent.putExtra(USERNAME_KEY, args);
            startActivity(intent);
        }
    };

    public static ProjectsFragment newInstance() {
        Bundle args = new Bundle();
        ProjectsFragment fragment = new ProjectsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = ProjectsBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.title_projects);
        }

        Toothpick.inject(this, AppDelegate.getAppScope());
        mBinding.setVm(mProjectsViewModel);
        mProjectsViewModel.setOnClickListener(mOnItemClickListener);
        mProjectsViewModel.loadProjects();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mProjectsViewModel.dispatchDetach();
    }
}
