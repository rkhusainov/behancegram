package com.github.rkhusainov.behancegram.ui.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.rkhusainov.behancegram.R;
import com.github.rkhusainov.behancegram.ui.profile.ProfileActivity;
import com.github.rkhusainov.behancegram.ui.profile.ProfileFragment;
import com.github.rkhusainov.behancegram.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.github.rkhusainov.behancegram.BuildConfig.API_QUERY;
import static com.github.rkhusainov.behancegram.ui.profile.ProfileActivity.USERNAME_KEY;
import static com.github.rkhusainov.behancegram.ui.profile.ProfileFragment.PROFILE_KEY;

public class ProjectsFragment extends Fragment implements ProjectsAdapter.OnItemClickListener {

    private ProjectsAdapter mProjectsAdapter;
    private RecyclerView mRecyclerView;
    private View mErrorView;
    private Disposable mDisposable;

    public static ProjectsFragment newInstance() {

        Bundle args = new Bundle();

        ProjectsFragment fragment = new ProjectsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getProjects();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_projects, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recycler);
        mErrorView = view.findViewById(R.id.errorView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null) {
            getActivity().setTitle(R.string.title_projects);
        }

        mProjectsAdapter = new ProjectsAdapter(this);
        mRecyclerView.setAdapter(mProjectsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onItemClick(String userName) {
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        Bundle args = new Bundle();
        args.putString(PROFILE_KEY, userName);
        intent.putExtra(USERNAME_KEY, args);
        startActivity(intent);
    }

    private void getProjects() {
        mDisposable = ApiUtils.getApi().getProjects(API_QUERY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(projectResponse -> {
                            mErrorView.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mProjectsAdapter.addData(projectResponse.getProjects());
                        },
                        throwable -> {
                            mErrorView.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.GONE);
                        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
