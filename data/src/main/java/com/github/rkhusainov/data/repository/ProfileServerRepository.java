package com.github.rkhusainov.data.repository;

import com.github.rkhusainov.data.api.BehanceApi;
import com.github.rkhusainov.domain.model.user.User;
import com.github.rkhusainov.domain.model.user.UserResponse;
import com.github.rkhusainov.domain.repository.ProfileRepository;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class ProfileServerRepository implements ProfileRepository {

    @Inject
    BehanceApi mApi;

    @Inject
    public ProfileServerRepository() {
    }

    @Override
    public Single<User> getUser(String username) {
        return mApi.getUserInfo(username).map(new Function<UserResponse, User>() {
            @Override
            public User apply(UserResponse userResponse) throws Exception {
                return userResponse.getUser();
            }
        });
    }

    @Override
    public void insertUser(User user) {
        // do nothing
    }
}
