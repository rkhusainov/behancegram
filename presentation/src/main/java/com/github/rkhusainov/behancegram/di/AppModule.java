package com.github.rkhusainov.behancegram.di;

import androidx.room.Room;

import com.github.rkhusainov.behancegram.AppDelegate;
import com.github.rkhusainov.data.database.BehanceDao;
import com.github.rkhusainov.data.database.BehanceDatabase;

import toothpick.config.Module;

public class AppModule extends Module {

    private final AppDelegate mApp;

    public AppModule(AppDelegate app) {
        mApp = app;
        bind(AppDelegate.class).toInstance(mApp);
        bind(BehanceDao.class).toInstance(provideStorage());
    }

    AppDelegate provideApp() {
        return mApp;
    }

    BehanceDao provideStorage() {
        final BehanceDatabase database = Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();

        return database.getBehanceDao();
    }

    BehanceDatabase provideDatabase() {
        return Room.databaseBuilder(mApp, BehanceDatabase.class, "behance_database")
                .fallbackToDestructiveMigration()
                .build();
    }

    BehanceDao provideBehanceDao(BehanceDatabase database) {
        return database.getBehanceDao();
    }
}
