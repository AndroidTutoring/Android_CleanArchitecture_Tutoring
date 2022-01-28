package com.example.presentation

import com.example.recylcerviewtest01.GithubService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitService {

    val Createretrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubService::class.java)
}