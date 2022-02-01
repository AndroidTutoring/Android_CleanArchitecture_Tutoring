package com.example.presentation.source.remote

import com.example.presentation.model.SearchedUsers
import com.example.presentation.retrofit.RetrofitHelper
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

class UserRemoteDataSourceImpl:UserRemoteDataSource {

    //유저 검색
    override fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): Single<Response<SearchedUsers>> {
       return RetrofitHelper.apiServices.searchUsers(query, page, perPage)
    }

}