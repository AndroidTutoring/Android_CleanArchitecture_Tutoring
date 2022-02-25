package com.example.local.di

import android.content.Context
import androidx.room.Room
import com.example.data.source.local.UserLocalDataSource
import com.example.local.impl.UserLocalDataSourceImpl
import com.example.local.room.FavoriteDao
import com.example.local.room.LocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideUserLocalDatasource(
        favoriteDao: FavoriteDao
    ): UserLocalDataSource =
        UserLocalDataSourceImpl(favoriteDao)


    @Singleton
    @Provides
    fun provideFavoriteDao(
        localDataBase: LocalDataBase
    ): FavoriteDao =
        localDataBase.getFavoriteMarkDao()


    @Singleton
    @Provides
    fun provideLocalDataBase(@ApplicationContext context: Context):LocalDataBase =
         Room.databaseBuilder(
            context.applicationContext,
            LocalDataBase::class.java, "local-database.db"
        ).fallbackToDestructiveMigration()//버전 업데이트시 기존 데이터 못찾으면, illegalStateException  방지
            .allowMainThreadQueries()
            .build()


}