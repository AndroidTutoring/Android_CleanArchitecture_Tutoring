package com.example.presentation.retrofit

import com.example.presentation.model.SearchedUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //깃헙 유저 검색 하기
    @GET("/search/users")
    fun searchUsers(
        @Query("q") query:String,
        @Query("page") page:Int,
        @Query("per_page") perPage:Int
    ): Call<SearchedUsers>

}