package com.example.presentation.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
object LocalDataModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context : Context
    ) = Room.databaseBuilder(
        context,
        RoomDatabase::class.java,
        "database"
    ).build()

    @Singleton
    @Provides
    fun provideDao(db:com.example.localdata.room.RoomDatabase) = db.userDao()

}