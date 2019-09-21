package com.github.rkhusainov.domain.service;

import com.github.rkhusainov.domain.model.project.Project;

import java.util.List;

import io.reactivex.Single;

public interface ProjectService {
    Single<List<Project>> getProjects();

    void insertProjects(List<Project> projects);
}
