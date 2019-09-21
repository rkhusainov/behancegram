package com.github.rkhusainov.behancegram.di;

import androidx.room.Room;

import com.github.rkhusainov.behancegram.AppDelegate;
import com.github.rkhusainov.behancegram.data.Storage;
import com.github.rkhusainov.behancegram.data.database.BehanceDatabase;

import toothpick.config.Module;

public class AppModule extends Module {

    private final AppDelegate mApp;

    public AppModule(AppDelegate app) {
        mApp = app;
        bind(AppDelegate.class).toInstance(mApp);
        bind(Storage.class).toInstance(provideStorage());
    }

    AppDelegate provideApp() {
        return mApp;
    }

    Storage provideStorage() {
        final BehanceDatabase database = Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        return new Storage(database.getBehanceDao());
    }
}
