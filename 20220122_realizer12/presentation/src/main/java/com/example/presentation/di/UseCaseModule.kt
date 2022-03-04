package com.example.presentation.di

import com.example.data.source.remote.UserRemoteDataSource
import com.example.domain.repository.UserRepoRepository
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.*
import com.example.remote.impl.UserRemoteDataSourceImpl
import com.example.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideGetSearchedUsersListUseCase(
        userRepository:UserRepository
    ): GetSearchedUsersListUseCase =
        GetSearchedUsersListUseCase(userRepository)

    @Singleton
    @Provides
    fun provideAddFavoriteUsersUseCase(
        userRepository:UserRepository
    ): AddFavoriteUsersUseCase =
        AddFavoriteUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun provideDeleteFavoriteUsersUseCase(
        userRepository:UserRepository
    ): DeleteFavoriteUseCase =
        DeleteFavoriteUseCase(userRepository)
    @Singleton
    @Provides
    fun provideGetFavoriteUsersUseCase(
        userRepository:UserRepository
    ): GetFavoriteUsersUseCase =
        GetFavoriteUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun provideUpdateSearchedUsersFavoriteUseCase(
        getFavoriteUsersUseCase: GetFavoriteUsersUseCase
    ): UpdateSearchedUsersFavoriteUseCase =
        UpdateSearchedUsersFavoriteUseCase(getFavoriteUsersUseCase)
}