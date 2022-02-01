package com.example.presentation.retrofit

import com.example.presentation.model.SearchedUsers
import com.example.presentation.model.UserRepo
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //깃헙 유저 검색 하기
    @GET("/search/users")
    fun searchUsers(
        @Query("q") query:String,
        @Query("page") page:Int,
        @Query("per_page") perPage:Int
    ): Single<Response<SearchedUsers>>

    //깃헙 유저 검색 하기
    @GET("/users/{userName}/repos")
    fun getUserRepoInfo(
      @Path("userName") userName:String
    ): Call<ArrayList<UserRepo>>

}