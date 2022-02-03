package com.example.presentation.source.remote

import com.example.presentation.model.SearchedUsers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface UserRemoteDataSource {
    fun getSearchUsers(
        query:String,
        page:Int,
        perPage:Int
    ):Single<Response<SearchedUsers>>
}