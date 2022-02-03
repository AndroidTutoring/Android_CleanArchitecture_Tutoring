package com.example.presentation

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object GithubRemoteRepoImpl {

    private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    val service : GithubRemoteRepo = retrofit.create(
        GithubRemoteRepo::class.java
    )


}