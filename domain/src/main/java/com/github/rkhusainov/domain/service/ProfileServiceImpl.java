package com.github.rkhusainov.domain.service;

import com.github.rkhusainov.domain.ApiUtils;
import com.github.rkhusainov.domain.model.user.User;
import com.github.rkhusainov.domain.repository.ProfileRepository;
import com.github.rkhusainov.domain.repository.ProjectRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ProfileServiceImpl implements ProfileService {

    @Inject
    @Named(ProjectRepository.SERVER)
    ProfileRepository mServerRepository;

    @Inject
    @Named(ProfileRepository.DB)
    ProfileRepository mDBRepository;

    @Inject
    public ProfileServiceImpl() {
    }

    @Override
    public Single<User> getUser(String username) {
        return mServerRepository.getUser(username)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(response -> mDBRepository.insertUser(response))
                .onErrorReturn(throwable ->
                        ApiUtils.NETWORK_EXCEPTIONS.contains(throwable.getClass())
                                ? mDBRepository.getUser(username).blockingGet()
                                : null);
    }

    @Override
    public void insertUser(User user) {
        // do nothing
    }
}
