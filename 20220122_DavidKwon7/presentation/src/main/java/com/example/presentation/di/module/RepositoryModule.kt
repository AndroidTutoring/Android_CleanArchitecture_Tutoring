package com.example.presentation.di.module

import com.example.data.repository.githubRepository.GithubRepository
import com.example.data.repository.githubRepository.GithubRepositoryImpl
import com.example.data.repository.githubSource.local.LocalDataSource
import com.example.data.repository.githubSource.remote.RemoteDataSource
import com.example.localdata.source.LocalDataSourceImpl
import com.example.remotedata.source.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule{

    @Binds
    abstract fun provideLocalDataSource(localDataSourceImpl: LocalDataSourceImpl) : LocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl) : RemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun provideRepository(repositoryImpl: GithubRepositoryImpl) : GithubRepository
}
