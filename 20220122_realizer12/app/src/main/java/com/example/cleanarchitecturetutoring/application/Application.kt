package com.example.cleanarchitecturetutoring.application

import android.app.Application
import timber.log.Timber


class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(TimberDebugTree())
    }
}