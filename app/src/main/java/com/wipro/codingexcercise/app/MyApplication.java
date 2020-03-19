package com.wipro.codingexcercise.app;


import android.app.Application;

/**
 * @author jayeshhiray
 * This is the application class where all the important initialization done here
 */
public class MyApplication extends Application {

    public static MyApplication instance;

    public static Application getInstance() {
        if (instance == null)
            instance = new MyApplication();

        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getInstance();
    }

}



