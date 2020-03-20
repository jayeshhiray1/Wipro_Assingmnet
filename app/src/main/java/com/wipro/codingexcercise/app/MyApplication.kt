package com.wipro.codingexcercise.app

import android.app.Application
import android.content.Context
import com.wipro.codingexcercise.HomeActivity

/**
 * @author jayeshhiray
 * This is the application class where all the important initialization done here
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        getInstance()
    }

    companion object {
        var instance: MyApplication? = null
        fun getInstance(): Application? {
            if (instance == null) instance = MyApplication()
            return instance
        }

    }

}