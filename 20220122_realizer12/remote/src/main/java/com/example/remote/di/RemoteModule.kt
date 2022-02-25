package com.example.remote.di

import com.example.data.source.remote.RepoRemoteDataSource
import com.example.data.source.remote.UserRemoteDataSource
import com.example.remote.impl.RepoRemoteDataSourceImpl
import com.example.remote.impl.UserRemoteDataSourceImpl
import com.example.remote.retrofit.ApiService
import com.example.remote.retrofit.ServerIp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(
        apiService: ApiService
    ): UserRemoteDataSource =
        UserRemoteDataSourceImpl(apiService)


    @Singleton
    @Provides
    fun provideRepoRemoteDataSource(
        apiService: ApiService
    ): RepoRemoteDataSource =
        RepoRemoteDataSourceImpl(apiService)


    @Singleton
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService =
        retrofit.create(ApiService::class.java)


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ServerIp.BaseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()



}
