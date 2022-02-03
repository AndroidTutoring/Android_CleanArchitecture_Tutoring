package com.example.mvctutorial.app

import android.app.Application
import com.example.recylcerviewtest01.R
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(TimberDebugTree(getString(R.string.app_name)))
    }
}