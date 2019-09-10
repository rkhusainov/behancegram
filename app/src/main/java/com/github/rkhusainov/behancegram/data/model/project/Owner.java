package com.github.rkhusainov.behancegram.data.model.project;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Owner implements Serializable {

    private int mId;

    @SerializedName("username")
    private String mUsername;

    private int mProjectId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(@NonNull String username) {
        mUsername = username;
    }

    public int getProjectId() {
        return mProjectId;
    }

    public void setProjectId(int projectId) {
        mProjectId = projectId;
    }
}
