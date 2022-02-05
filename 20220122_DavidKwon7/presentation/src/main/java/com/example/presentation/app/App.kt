package com.example.presentation.app

import android.app.Application
import com.example.presentation.R
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(TimberDebugTree(getString(R.string.app_name)))
    }
}