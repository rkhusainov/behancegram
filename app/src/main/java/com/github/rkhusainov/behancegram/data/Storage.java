package com.github.rkhusainov.behancegram.data;

import com.github.rkhusainov.behancegram.data.database.BehanceDao;
import com.github.rkhusainov.behancegram.data.model.project.Cover;
import com.github.rkhusainov.behancegram.data.model.project.Owner;
import com.github.rkhusainov.behancegram.data.model.project.Project;
import com.github.rkhusainov.behancegram.data.model.project.ProjectResponse;
import com.github.rkhusainov.behancegram.data.model.user.Image;
import com.github.rkhusainov.behancegram.data.model.user.User;
import com.github.rkhusainov.behancegram.data.model.user.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private BehanceDao mBehanceDao;

    public Storage(BehanceDao behanceDao) {
        mBehanceDao = behanceDao;
    }

    public void insertProjects(ProjectResponse response) {
        List<Project> projects = response.getProjects();
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

        mBehanceDao.insertCovers(covers);
        mBehanceDao.insertOwners(owners);
    }

    public ProjectResponse getProjects() {
        List<Project> projects = mBehanceDao.getProjects();

        // get covers and owners from DB and add to project
        for (Project project : projects) {
            project.setCover(mBehanceDao.getCoverFromProject(project.getId()));
            project.setOwners(mBehanceDao.getOwnersFromProject(project.getId()));
        }

        ProjectResponse response = new ProjectResponse();
        response.setProjects(projects);

        return response;
    }

    public void insertUser(UserResponse response) {
        User user = response.getUser();
        Image image = user.getImage();
        image.setUserId(user.getId());
        mBehanceDao.insertUser(user);
        mBehanceDao.insertImage(image);
    }

    public UserResponse getUser(String username) {

        User user = mBehanceDao.getUserByName(username);
        // get image from DB and add to user
        Image image = mBehanceDao.getImageFromUser(user.getId());
        user.setImage(image);

        UserResponse response = new UserResponse();
        response.setUser(user);

        return response;
    }

    public interface StorageOwner {
        Storage obtainStorage();
    }
}
