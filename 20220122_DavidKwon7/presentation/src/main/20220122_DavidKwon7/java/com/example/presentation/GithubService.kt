package com.example.recylcerviewtest01

import com.example.myapplication.model.userSearch.userSearchModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users/{owner}/repos")
    fun getRepos(@Path("owner") owner: String)
    : Call<List<GithubRepo>>


    @GET("/search/users")
    fun getUserSearch(
        @Query("q") q: String,
        @Query("page") page: String,
        @Query("per_page") per_page: String
    ) : Call<userSearchModel>

}