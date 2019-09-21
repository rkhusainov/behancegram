package com.github.rkhusainov.data.repository;

import com.github.rkhusainov.data.api.BehanceApi;
import com.github.rkhusainov.domain.model.project.Project;
import com.github.rkhusainov.domain.model.project.ProjectResponse;
import com.github.rkhusainov.domain.repository.ProjectRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

import static com.github.rkhusainov.data.BuildConfig.API_QUERY;

public class ProjectServerRepository implements ProjectRepository {

    @Inject
    BehanceApi mApi;

    @Inject
    public ProjectServerRepository() {
    }

    @Override
    public Single<List<Project>> getProjects() {
        return mApi.getProjects(API_QUERY).map(new Function<ProjectResponse, List<Project>>() {
            @Override
            public List<Project> apply(ProjectResponse projectResponse) throws Exception {
                return projectResponse.getProjects();
            }
        });
    }

    @Override
    public void insertProjects(List<Project> projects) {
        // do nothing
    }
}
