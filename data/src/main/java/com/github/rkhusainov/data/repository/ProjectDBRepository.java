package com.github.rkhusainov.data.repository;

import com.github.rkhusainov.data.database.BehanceDao;
import com.github.rkhusainov.domain.model.project.Cover;
import com.github.rkhusainov.domain.model.project.Owner;
import com.github.rkhusainov.domain.model.project.Project;
import com.github.rkhusainov.domain.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;

public class ProjectDBRepository implements ProjectRepository {

    @Inject
    BehanceDao mBehanceDao;

    @Inject
    public ProjectDBRepository() {
    }

    @Override
    public Single<List<Project>> getProjects() {

        return Single.fromCallable(new Callable<List<Project>>() {
            @Override
            public List<Project> call() throws Exception {
                List<Project> projects = mBehanceDao.getProjects();

                // get covers and owners from DB and add to project
                for (Project project : projects) {
                    project.setCover(mBehanceDao.getCoverFromProject(project.getId()));
                    project.setOwners(mBehanceDao.getOwnersFromProject(project.getId()));
                }
                return projects;
            }
        });
    }

    @Override
    public void insertProjects(List<Project> projects) {
        mBehanceDao.insertProjects(projects);

        // save covers and owners
        List<Cover> covers = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();

        for (int i = 0; i < projects.size(); i++) {
            Cover cover = projects.get(i).getCover();
            cover.setId(i);
            cover.setProjectId(projects.get(i).getId());
            covers.add(cover);

            Owner owner = projects.get(i).getOwners().get(0);
            owner.setId(i);
            owner.setProjectId(projects.get(i).getId());
            owners.add(owner);
        }

        mBehanceDao.clearCoverTable();
        mBehanceDao.insertCovers(covers);
        mBehanceDao.clearOwnerTable();
        mBehanceDao.insertOwners(owners);
    }
}
