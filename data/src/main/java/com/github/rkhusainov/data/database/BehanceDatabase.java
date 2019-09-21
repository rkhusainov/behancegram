package com.github.rkhusainov.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.github.rkhusainov.data.model.project.Cover;
import com.github.rkhusainov.data.model.project.Owner;
import com.github.rkhusainov.data.model.project.Project;
import com.github.rkhusainov.data.model.user.Image;
import com.github.rkhusainov.data.model.user.User;

@Database(entities = {Project.class, Cover.class, Owner.class, User.class, Image.class}, version = 1)
public abstract class BehanceDatabase extends RoomDatabase {
    public abstract BehanceDao getBehanceDao();
}
