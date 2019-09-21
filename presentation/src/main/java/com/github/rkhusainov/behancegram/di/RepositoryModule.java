package com.github.rkhusainov.behancegram.di;


import com.github.rkhusainov.data.repository.ProfileDBRepository;
import com.github.rkhusainov.data.repository.ProfileServerRepository;
import com.github.rkhusainov.data.repository.ProjectDBRepository;
import com.github.rkhusainov.data.repository.ProjectServerRepository;
import com.github.rkhusainov.domain.repository.ProfileRepository;
import com.github.rkhusainov.domain.repository.ProjectRepository;

import toothpick.config.Module;

public class RepositoryModule extends Module {

    public RepositoryModule() {
        bind(ProjectRepository.class).withName(ProjectRepository.SERVER).to(ProjectServerRepository.class);
        bind(ProjectRepository.class).withName(ProjectRepository.DB).to(ProjectDBRepository.class);
        bind(ProfileRepository.class).withName(ProfileRepository.SERVER).to(ProfileServerRepository.class);
        bind(ProfileRepository.class).withName(ProfileRepository.DB).to(ProfileDBRepository.class);
    }
}
