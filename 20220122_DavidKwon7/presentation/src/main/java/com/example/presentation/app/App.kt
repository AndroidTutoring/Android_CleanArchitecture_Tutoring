package com.example.presentation.app

import android.app.Application
import com.example.presentation.R
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(TimberDebugTree(getString(R.string.app_name)))
    }
}