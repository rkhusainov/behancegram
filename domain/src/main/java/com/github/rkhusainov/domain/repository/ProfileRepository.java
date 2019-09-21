package com.github.rkhusainov.domain.repository;

import com.github.rkhusainov.domain.model.user.User;

import io.reactivex.Single;

public interface ProfileRepository {

    String SERVER = "SERVER";
    String DB = "DB";

    Single<User> getUser(String username);

    void insertUser(User user);
}
