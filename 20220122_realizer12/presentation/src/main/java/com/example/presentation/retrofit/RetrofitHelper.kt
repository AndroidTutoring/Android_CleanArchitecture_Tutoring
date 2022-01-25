package com.example.presentation.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val apiServices: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ServerIp.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}