package com.github.rkhusainov.behancegram.ui.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.rkhusainov.behancegram.R;
import com.github.rkhusainov.behancegram.data.Storage;
import com.github.rkhusainov.behancegram.databinding.ProjectsBinding;
import com.github.rkhusainov.behancegram.ui.profile.ProfileActivity;

import static com.github.rkhusainov.behancegram.ui.profile.ProfileActivity.USERNAME_KEY;
import static com.github.rkhusainov.behancegram.ui.profile.ProfileFragment.PROFILE_KEY;

public class ProjectsFragment extends Fragment {

    private ProjectsViewModel mProjectsViewModel;
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Storage.StorageOwner) {
            Storage storage = ((Storage.StorageOwner) context).obtainStorage();
            mProjectsViewModel = new ProjectsViewModel(storage, mOnItemClickListener);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ProjectsBinding binding = ProjectsBinding.inflate(inflater, container, false);
        binding.setVm(mProjectsViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.title_projects);
        }
        mProjectsViewModel.loadProjects();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mProjectsViewModel.dispatchDetach();
    }
}
