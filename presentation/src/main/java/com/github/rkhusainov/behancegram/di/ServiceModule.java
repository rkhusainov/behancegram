package com.github.rkhusainov.behancegram.di;

import com.github.rkhusainov.domain.service.ProfileService;
import com.github.rkhusainov.domain.service.ProfileServiceImpl;
import com.github.rkhusainov.domain.service.ProjectService;
import com.github.rkhusainov.domain.service.ProjectServiceImpl;

import toothpick.config.Module;

public class ServiceModule extends Module {
    public ServiceModule() {
        bind(ProjectService.class).to(ProjectServiceImpl.class);
        bind(ProfileService.class).to(ProfileServiceImpl.class);
    }
}
