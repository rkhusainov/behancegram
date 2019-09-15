package com.github.rkhusainov.behancegram.ui.project;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.rkhusainov.behancegram.data.model.project.FullProject;
import com.github.rkhusainov.behancegram.databinding.ProjectBinding;

import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsHolder> {

    private final List<FullProject> mProjects;
    private final OnItemClickListener mOnItemClickListener;


    public ProjectsAdapter(List<FullProject> projects, OnItemClickListener onItemClickListener) {
        mProjects = projects;
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ProjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ProjectBinding binding = ProjectBinding.inflate(inflater, parent, false);
        return new ProjectsHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsHolder holder, int position) {
        FullProject fullProject = mProjects.get(position);
        holder.bind(fullProject, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        // mProjects from database maybe null so we need check it for null
        return mProjects == null ? 0 : mProjects.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String userName);
    }
}
