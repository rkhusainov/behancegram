package com.github.rkhusainov.data.repository;

import com.github.rkhusainov.data.database.BehanceDao;
import com.github.rkhusainov.domain.model.user.Image;
import com.github.rkhusainov.domain.model.user.User;
import com.github.rkhusainov.domain.repository.ProfileRepository;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;

public class ProfileDBRepository implements ProfileRepository {

    @Inject
    BehanceDao mBehanceDao;


    @Override
    public Single<User> getUser(final String username) {

        return Single.fromCallable(new Callable<User>() {
            @Override
            public User call() throws Exception {
                User user = mBehanceDao.getUserByName(username);
                // get image from DB and add to user
                Image image = mBehanceDao.getImageFromUser(user.getId());
                user.setImage(image);

                return user;
            }
        });
    }

    @Override
    public void insertUser(User user) {
        Image image = user.getImage();
        image.setUserId(user.getId());
        mBehanceDao.insertUser(user);
        mBehanceDao.insertImage(image);
    }
}
