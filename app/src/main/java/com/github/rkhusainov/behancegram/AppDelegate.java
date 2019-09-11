package com.github.rkhusainov.behancegram;

import android.app.Application;

import androidx.room.Room;

import com.github.rkhusainov.behancegram.data.Storage;
import com.github.rkhusainov.behancegram.data.database.BehanceDatabase;

public class AppDelegate extends Application {

    private Storage mStorage;

    @Override
    public void onCreate() {
        super.onCreate();

        final BehanceDatabase database = Room.databaseBuilder(this, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        mStorage = new Storage(database.getBehanceDao());
    }

    public Storage getStorage() {
        return mStorage;
    }
}
