package com.github.rkhusainov.behancegram.ui.project;

import androidx.recyclerview.widget.RecyclerView;

import com.github.rkhusainov.behancegram.data.model.project.Project;
import com.github.rkhusainov.behancegram.databinding.ProjectBinding;

public class ProjectsHolder extends RecyclerView.ViewHolder {

    private ProjectBinding mProjectBinding;

    public ProjectsHolder(ProjectBinding binding) {
        super(binding.getRoot());

        mProjectBinding = binding;
    }

    public void bind(Project project, ProjectsAdapter.OnItemClickListener onItemClickListener) {

        mProjectBinding.setProject(new ProjectItemListViewModel(project));
        mProjectBinding.setOnItemClickListener(onItemClickListener);
        mProjectBinding.executePendingBindings();

    }
}
