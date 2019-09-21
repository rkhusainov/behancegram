package com.github.rkhusainov.domain.service;

import com.github.rkhusainov.domain.model.user.User;

import io.reactivex.Single;

public interface ProfileService {
    Single<User> getUser(String username);

    void insertUser(User user);
}
