package com.github.rkhusainov.domain.service;

import com.github.rkhusainov.domain.ApiUtils;
import com.github.rkhusainov.domain.model.project.Project;
import com.github.rkhusainov.domain.repository.ProjectRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ProjectServiceImpl implements ProjectService {

    @Inject
    @Named(ProjectRepository.SERVER)
    ProjectRepository mServerRepository;

    @Inject
    @Named(ProjectRepository.DB)
    ProjectRepository mDBRepository;

    @Inject
    public ProjectServiceImpl() {
    }


    @Override
    public Single<List<Project>> getProjects() {
        return mServerRepository.getProjects()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(mDBRepository::insertProjects)
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())
                                ? mDBRepository.getProjects().blockingGet()
                                : null);
    }

    @Override
    public void insertProjects(List<Project> projects) {
        mDBRepository.insertProjects(projects);
    }
}
