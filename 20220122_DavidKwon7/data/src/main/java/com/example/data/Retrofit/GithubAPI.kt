package com.example.presentation

import com.example.data.model.UserDataModel
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPI {
    @GET("users/{owner}/repos")
    fun getRepos(@Path("owner") owner: String)
            : Single<List<UserDataModel>>

}



