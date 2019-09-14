package com.github.rkhusainov.behancegram.ui.project;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.rkhusainov.behancegram.data.Storage;
import com.github.rkhusainov.behancegram.data.model.project.Project;
import com.github.rkhusainov.behancegram.utils.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.github.rkhusainov.behancegram.BuildConfig.API_QUERY;

public class ProjectsViewModel extends ViewModel {

    private Storage mStorage;
    private ProjectsAdapter.OnItemClickListener mListener;
    private Disposable mDisposable;

    private MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsErrorVisible = new MutableLiveData<>();
    private MutableLiveData<List<Project>> mProjects = new MutableLiveData<>();
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadProjects();
        }
    };


    public ProjectsViewModel(Storage storage, ProjectsAdapter.OnItemClickListener listener) {
        mStorage = storage;
        mListener = listener;
        mProjects.setValue(new ArrayList<>());
        loadProjects();
    }

    public void loadProjects() {
        mDisposable = ApiUtils.getApi().getProjects(API_QUERY)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mStorage.insertProjects(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass()) ? mStorage.getProjects() : null)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mIsLoading.postValue(true))
                .doFinally(() -> mIsLoading.postValue(false))
                .subscribe(projectResponse -> {
                            mIsErrorVisible.postValue(false);
                            mProjects.postValue(projectResponse.getProjects());
                        },
                        throwable -> {
                            mIsErrorVisible.postValue(true);
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

    public MutableLiveData<List<Project>> getProjects() {
        return mProjects;
    }

    public SwipeRefreshLayout.OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }
}
