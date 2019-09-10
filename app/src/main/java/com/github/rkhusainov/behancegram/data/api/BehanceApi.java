package com.github.rkhusainov.behancegram.data.api;

import com.github.rkhusainov.behancegram.data.model.project.ProjectResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BehanceApi {
    @GET("v2/projects")
    Single<ProjectResponse> getProjects(@Query("q") String query);
}
