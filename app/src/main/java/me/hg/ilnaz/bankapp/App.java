package me.hg.ilnaz.bankapp;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by ilnaz on 27.05.16, 3:15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
