package com.github.rkhusainov.behancegram.data.model.user;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    private int mId;

    @SerializedName("username")
    private String mUsername;

    @SerializedName("location")
    private String mLocation;

    @SerializedName("created_on")
    private long mCreatedOn;

    @SerializedName("images")
    private Image mImage;

    @SerializedName("display_name")
    private String mDisplayName;

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

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(@NonNull String location) {
        mLocation = location;
    }

    public long getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(long createdOn) {
        mCreatedOn = createdOn;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(@NonNull Image image) {
        mImage = image;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(@NonNull String displayName) {
        mDisplayName = displayName;
    }
}
