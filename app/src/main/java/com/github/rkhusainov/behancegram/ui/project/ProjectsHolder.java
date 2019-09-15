package com.github.rkhusainov.behancegram.ui.project;

import androidx.recyclerview.widget.RecyclerView;

import com.github.rkhusainov.behancegram.data.model.project.FullProject;
import com.github.rkhusainov.behancegram.databinding.ProjectBinding;

public class ProjectsHolder extends RecyclerView.ViewHolder {

    private ProjectBinding mProjectBinding;

    public ProjectsHolder(ProjectBinding binding) {
        super(binding.getRoot());
        mProjectBinding = binding;
    }

    public void bind(FullProject fullProject, ProjectsAdapter.OnItemClickListener onItemClickListener) {
        mProjectBinding.setProject(new ProjectItemListViewModel(fullProject));
        mProjectBinding.setOnItemClickListener(onItemClickListener);
        mProjectBinding.executePendingBindings();
    }
}
