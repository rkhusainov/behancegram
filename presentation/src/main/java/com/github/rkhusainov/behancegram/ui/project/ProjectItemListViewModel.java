package com.github.rkhusainov.behancegram.ui.project;

import com.github.rkhusainov.domain.model.project.Project;
import com.github.rkhusainov.behancegram.utils.DateUtils;

public class ProjectItemListViewModel {

    private static final int FIRST_OWNER_INDEX = 0;

    private String mImageUrl;
    private String mName;
    private String mUsername;
    private String mPublishedOn;

    public ProjectItemListViewModel(Project project) {
        mImageUrl = project.getCover().getPhotoUrl();
        mName = project.getName();
        mUsername = project.getOwners().get(FIRST_OWNER_INDEX).getUsername();
        mPublishedOn = DateUtils.format(project.getPublishedOn());
    }


    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPublishedOn() {
        return mPublishedOn;
    }
}
