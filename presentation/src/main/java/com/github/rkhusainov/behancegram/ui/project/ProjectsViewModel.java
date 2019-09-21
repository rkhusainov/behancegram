package com.github.rkhusainov.behancegram.ui.project;

import android.annotation.SuppressLint;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.rkhusainov.domain.model.project.Project;
import com.github.rkhusainov.domain.service.ProjectService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ProjectsViewModel {


    @Inject
    ProjectService mService;

    private ProjectsAdapter.OnItemClickListener mOnItemClickListener;
    private Disposable mDisposable;

    private ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private ObservableBoolean mIsErrorVisible = new ObservableBoolean(false);
    private ObservableArrayList<Project> mProjects = new ObservableArrayList<>();
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadProjects();
        }
    };

    @Inject
    public ProjectsViewModel() {
    }


    @SuppressLint("CheckResult")
    public void loadProjects() {
        mDisposable = mService.getProjects()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.set(true))
                .doFinally(() -> mIsLoading.set(false))
                .subscribe(response -> {
                            mIsErrorVisible.set(false);
                            if (!mProjects.isEmpty()) {
                                mProjects.clear();
                            }
                            mProjects.addAll(response);
                        },
                        throwable -> mIsErrorVisible.set(true));
    }

    public ProjectsAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public ObservableBoolean getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public ObservableArrayList<Project> getProjects() {
        return mProjects;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void setOnClickListener(ProjectsAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void dispatchDetach() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
