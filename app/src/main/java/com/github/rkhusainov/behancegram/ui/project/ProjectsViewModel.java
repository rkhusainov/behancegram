package com.github.rkhusainov.behancegram.ui.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.rkhusainov.behancegram.data.Storage;
import com.github.rkhusainov.behancegram.data.model.project.FullProject;
import com.github.rkhusainov.behancegram.data.model.project.ProjectResponse;
import com.github.rkhusainov.behancegram.utils.ApiUtils;

import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.github.rkhusainov.behancegram.BuildConfig.API_QUERY;

public class ProjectsViewModel extends ViewModel {
    private Storage mStorage;
    private ProjectsAdapter.OnItemClickListener mListener;
    private Disposable mDisposable;

    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();
    private LiveData<List<FullProject>> mProjects;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            updateProjects();
        }
    };

    public ProjectsViewModel(Storage storage, ProjectsAdapter.OnItemClickListener listener) {
        mStorage = storage;
        mListener = listener;
        mProjects = mStorage.getProjectsLive();
        updateProjects();
    }

    private void updateProjects() {
        mDisposable = ApiUtils.getApi().getProjects(API_QUERY)
                .map(ProjectResponse::getProjects)
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .doOnSuccess(response -> mIsErrorVisible.postValue(false))
                .subscribeOn(Schedulers.io())
                .subscribe(projectResponse -> {
                            mStorage.insertProjects(projectResponse);
                        },
                        throwable -> {
                            mIsErrorVisible.postValue(true);
                            boolean isEmptyProjects = mProjects.getValue() == null || mProjects.getValue().size() == 0;
                            mIsErrorVisible.postValue(isEmptyProjects);
                        });
    }

    @Override
    public void onCleared() {
        mStorage = null;
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    public ProjectsAdapter.OnItemClickListener getListener() {
        return mListener;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return mIsLoading;
    }

    public MutableLiveData<Boolean> getIsErrorVisible() {
        return mIsErrorVisible;
    }

    public LiveData<List<FullProject>> getProjects() {
        return mProjects;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }
}
