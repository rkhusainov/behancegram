package com.github.rkhusainov.behancegram.data.model.project;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cover implements Serializable {

    private int mId;

    @SerializedName("202")
    private String mPhotoUrl;

    private int mProjectId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(@NonNull String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public int getProjectId() {
        return mProjectId;
    }

    public void setProjectId(int projectId) {
        mProjectId = projectId;
    }
}
