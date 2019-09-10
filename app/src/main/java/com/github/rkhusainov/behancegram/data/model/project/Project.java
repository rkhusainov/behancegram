package com.github.rkhusainov.behancegram.data.model.project;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("published_on")
    private long mPublishedOn;

    @SerializedName("covers")
    private Cover mCover;

    @SerializedName("owners")
    private List<Owner> mOwners;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(@NonNull String name) {
        mName = name;
    }

    public long getPublishedOn() {
        return mPublishedOn;
    }

    public void setPublishedOn(long publishedOn) {
        mPublishedOn = publishedOn;
    }

    public Cover getCover() {
        return mCover;
    }

    public void setCover(@NonNull Cover cover) {
        mCover = cover;
    }

    public List<Owner> getOwners() {
        return mOwners;
    }

    public void setOwners(@NonNull List<Owner> owners) {
        mOwners = owners;
    }
}