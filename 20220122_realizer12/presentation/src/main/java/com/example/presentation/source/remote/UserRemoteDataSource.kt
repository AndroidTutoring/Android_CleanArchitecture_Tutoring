package com.example.presentation.source.remote

import com.example.presentation.model.SearchedUsers
import retrofit2.Call
import retrofit2.Callback

interface UserRemoteDataSource {
    fun getSearchUsers(
        query:String = "",
        page:Int,
        perPage:Int,
        onSuccess: (SearchedUsers?) -> Unit,
        onFailure: (call: Call<SearchedUsers>, callback: Callback<SearchedUsers>,errorBody:Int?) -> Unit,
    )
}