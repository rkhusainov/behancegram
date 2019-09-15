package com.github.rkhusainov.behancegram.data;

import androidx.lifecycle.LiveData;

import com.github.rkhusainov.behancegram.data.database.BehanceDao;
import com.github.rkhusainov.behancegram.data.model.project.FullProject;
import com.github.rkhusainov.behancegram.data.model.project.Owner;
import com.github.rkhusainov.behancegram.data.model.project.Project;
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


    public void insertProjects(List<Project> projects) {
        mBehanceDao.insertProjects(projects);

        // save owners
        List<Owner> owners = new ArrayList<>();

        for (int i = 0; i < projects.size(); i++) {
            Owner owner = projects.get(i).getOwners().get(0);
            owner.setId(i);
            owner.setProjectId(projects.get(i).getId());
            owners.add(owner);
        }

        mBehanceDao.insertOwners(owners);
    }

    public LiveData<List<FullProject>> getProjectsLive() {
        return mBehanceDao.getProjectsLive();
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
