package com.github.rkhusainov.behancegram.data.model.user;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class Image {

    private int mId;

    @SerializedName("230")
    private String mPhotoUrl;

    private int mUserId;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(@Nullable String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

}
