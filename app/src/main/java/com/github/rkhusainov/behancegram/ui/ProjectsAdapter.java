package com.github.rkhusainov.behancegram.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.rkhusainov.behancegram.R;
import com.github.rkhusainov.behancegram.data.model.project.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsHolder> {

    private final List<Project> mProjects = new ArrayList<>();

    public ProjectsAdapter() {
    }

    @NonNull
    @Override
    public ProjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_projects, parent, false);
        return new ProjectsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsHolder holder, int position) {
        Project project = mProjects.get(position);
        holder.bind(project);
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }

    public void addData(List<Project> projects) {
        mProjects.clear();
        mProjects.addAll(projects);
        notifyDataSetChanged();
    }
}
