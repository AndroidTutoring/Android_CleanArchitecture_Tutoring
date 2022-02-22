package com.example.presentation.di.module

import com.example.data.repository.githubRepository.GithubRepository
import com.example.data.repository.githubSource.local.LocalDataSource
import com.example.data.repository.githubSource.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule{

    @Binds
    abstract fun provideLocalDataSource(localDataSource: LocalDataSource) : LocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteDataSource) : RemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun provideRepository(repository: GithubRepository) : GithubRepository
}
