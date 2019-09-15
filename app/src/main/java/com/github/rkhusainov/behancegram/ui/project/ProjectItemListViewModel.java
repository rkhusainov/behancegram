package com.github.rkhusainov.behancegram.ui.project;

import com.github.rkhusainov.behancegram.data.model.project.FullProject;
import com.github.rkhusainov.behancegram.utils.DateUtils;

public class ProjectItemListViewModel {

    private static final int FIRST_OWNER_INDEX = 0;

    private String mImageUrl;
    private String mName;
    private String mUsername;
    private String mPublishedOn;

    public ProjectItemListViewModel(FullProject fullProject) {
        mImageUrl = fullProject.mProject.getCover().getPhotoUrl();
        mName = fullProject.mProject.getName();
        mPublishedOn = DateUtils.format(fullProject.mProject.getPublishedOn());
        // mOwners is relation so it maybe not available and we need to check
        if (fullProject.mOwners != null && fullProject.mOwners.size() != 0) {
            mUsername = fullProject.mOwners.get(FIRST_OWNER_INDEX).getUsername();
        }
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
