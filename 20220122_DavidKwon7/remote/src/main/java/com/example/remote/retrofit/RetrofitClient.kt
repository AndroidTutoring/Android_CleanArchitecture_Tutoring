package com.example.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var BASE_URL = "https://api.github.com/"
    private fun createClientAuth() : OkHttpClient {
        val okHttpClientBuilder : OkHttpClient.Builder = OkHttpClient.Builder()
        //로그 찍어주기
        okHttpClientBuilder.addNetworkInterceptor(
            HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpClientBuilder.build()
    }

    private val InstanceBuilder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createClientAuth())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    val createGithubAPI : com.example.remote.retrofit.GithubAPI by lazy {
        InstanceBuilder.create(
            com.example.remote.retrofit.GithubAPI::class.java
        )
    }
}