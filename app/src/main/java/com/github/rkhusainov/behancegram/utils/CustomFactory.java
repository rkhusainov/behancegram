package com.github.rkhusainov.behancegram.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.github.rkhusainov.behancegram.data.Storage;
import com.github.rkhusainov.behancegram.ui.project.ProjectsAdapter;
import com.github.rkhusainov.behancegram.ui.project.ProjectsViewModel;

public class CustomFactory extends ViewModelProvider.NewInstanceFactory {
    private Storage mStorage;
    private ProjectsAdapter.OnItemClickListener mItemClickListener;

    public CustomFactory(Storage storage, ProjectsAdapter.OnItemClickListener itemClickListener) {
        mStorage = storage;
        mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProjectsViewModel(mStorage, mItemClickListener);
    }
}
