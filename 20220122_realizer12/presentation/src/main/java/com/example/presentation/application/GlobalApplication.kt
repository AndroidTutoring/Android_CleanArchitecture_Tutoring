package com.example.presentation.application

import android.app.Application
import com.example.presentation.util.TimberDebugTree
import timber.log.Timber


class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(TimberDebugTree())
    }
}