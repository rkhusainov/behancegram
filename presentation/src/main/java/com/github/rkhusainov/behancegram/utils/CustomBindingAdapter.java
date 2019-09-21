package com.github.rkhusainov.behancegram.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.rkhusainov.data.model.project.Project;
import com.github.rkhusainov.behancegram.ui.project.ProjectsAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Picasso.get().load(imageUrl).into(imageView);
    }

    @BindingAdapter({"bind:data", "bind:clickHandler"})
    public static void configureRecyclerView(RecyclerView recyclerView, List<Project> projects, ProjectsAdapter.OnItemClickListener listener) {
        ProjectsAdapter adapter = new ProjectsAdapter(projects, listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter({"bind:refreshState", "bind:onRefresh"})
    public static void configureSwipreRefreshLayout(SwipeRefreshLayout refreshLayout, boolean isLoading, SwipeRefreshLayout.OnRefreshListener listener) {
        refreshLayout.setOnRefreshListener(listener);
        refreshLayout.post(() -> refreshLayout.setRefreshing(isLoading));
    }


}
