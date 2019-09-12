package com.github.rkhusainov.behancegram.ui.project;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.rkhusainov.behancegram.data.Storage;
import com.github.rkhusainov.behancegram.data.model.project.Project;
import com.github.rkhusainov.behancegram.utils.ApiUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.github.rkhusainov.behancegram.BuildConfig.API_QUERY;

public class ProjectsViewModel {

    private Storage mStorage;
    private ProjectsAdapter.OnItemClickListener mListener;
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


    public ProjectsViewModel(Storage storage, ProjectsAdapter.OnItemClickListener listener) {
        mStorage = storage;
        mListener = listener;
    }

    public void loadProjects() {
        mDisposable = ApiUtils.getApi().getProjects(API_QUERY)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.set(true))
                .doFinally(() -> mIsLoading.set(false))
                .subscribe(projectResponse -> {
                            mIsErrorVisible.set(false);
                            mProjects.addAll(projectResponse.getProjects());
                        },
                        throwable -> {
                            mIsErrorVisible.set(true);
                        });

    }

    public void dispatchDetach() {
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public ProjectsAdapter.OnItemClickListener getListener() {
        return mListener;
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
}
