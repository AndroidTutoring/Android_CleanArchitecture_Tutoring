package com.example.remote.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val apiServices: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ServerIp.BaseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}