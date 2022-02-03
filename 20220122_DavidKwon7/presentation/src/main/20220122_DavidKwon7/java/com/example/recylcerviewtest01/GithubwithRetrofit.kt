package com.example.recylcerviewtest01

import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("users/{owner}/repos")
    fun getRepos(@Path("owner") owner: String)
            : Single<List<User>>


}

class createRetrofit(){
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


}