package com.example.data.di

import com.example.data.repository.RepoRepository
import com.example.data.repository.RepoRepositoryImpl
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import com.example.data.source.local.UserLocalDataSource
import com.example.data.source.remote.RepoRemoteDataSource
import com.example.data.source.remote.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideUserRepository(
         localDataSource:UserLocalDataSource,
         remoteDataSource:UserRemoteDataSource
    ):UserRepository =
        UserRepositoryImpl(localDataSource, remoteDataSource)


    @Singleton
    @Provides
    fun provideRepoRepository(
        remoteDataSource:RepoRemoteDataSource
    ):RepoRepository =
         RepoRepositoryImpl(remoteDataSource)

}