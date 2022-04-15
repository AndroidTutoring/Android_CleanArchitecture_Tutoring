package com.example.presentation.di

import com.example.domain.repository.UserRepository
import com.example.domain.usecase.*
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
    fun provideGetSearchedUsersListMainUseCase(
        getFavoriteUsersUseCase: GetFavoriteUsersUseCase,
        getSearchedUsersListUseCase: GetSearchedUsersListUseCase
    ): GetSearchedUsersListInMainUseCase =
        GetSearchedUsersListInMainUseCase(getFavoriteUsersUseCase,getSearchedUsersListUseCase)

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

    @Singleton
    @Provides
    fun provideGetSearchedUsersUseCase(
        userRepository:UserRepository
    ): GetSearchedUsersListUseCase =
        GetSearchedUsersListUseCase(userRepository)

    @Singleton
    @Provides
    fun provideGetInitialSearchedUserUseCase(
        getSearchedUsersListUseCase: GetSearchedUsersListUseCase
    ): GetInitialSearchedUserUseCase =
        GetInitialSearchedUserUseCase(getSearchedUsersListUseCase)
}