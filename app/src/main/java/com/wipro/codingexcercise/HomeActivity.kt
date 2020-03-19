package com.wipro.codingexcercise

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.wipro.codingexcercise.ui.mvvm.view.MvvmFactFragment

/**
 * This is the main for View class
 */
class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    //MVVM pattern defined below
         loadMVVMFragment(MvvmFactFragment())

    }

    fun loadMVVMFragment(mvvmFragment: MvvmFactFragment) {
        //THIS BELOW CODE FOR MVVM pattern
        supportFragmentManager.beginTransaction().add(R.id.container, mvvmFragment).commitAllowingStateLoss()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            Log.i(TAG, "landscape")
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "portrait")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "MainActivity onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "MainActivity onSaveInstanceState")
    }

    companion object {
        val TAG = "HomeActivity"
    }
}
