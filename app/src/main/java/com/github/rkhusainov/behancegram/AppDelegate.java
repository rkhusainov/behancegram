package com.github.rkhusainov.behancegram;

import android.app.Application;

import com.github.rkhusainov.behancegram.di.AppModule;
import com.github.rkhusainov.behancegram.di.NetworkModule;

import toothpick.Scope;
import toothpick.Toothpick;
import toothpick.smoothie.module.SmoothieApplicationModule;

public class AppDelegate extends Application {

    private static Scope sAppScope;

    @Override
    public void onCreate() {
        super.onCreate();

        sAppScope = Toothpick.openScope(AppDelegate.class);
        sAppScope.installModules(new SmoothieApplicationModule(this), new NetworkModule(), new AppModule(this));
    }

    public static Scope getAppScope() {
        return sAppScope;
    }
}
