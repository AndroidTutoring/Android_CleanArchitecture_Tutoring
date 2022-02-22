package com.example.remote.impl

import com.example.data.model.SearchedUsers
import com.example.data.source.remote.UserRemoteDataSource
import com.example.remote.model.SearchedUsersRemote.Companion.toDataModel
import com.example.remote.retrofit.RetrofitHelper
import io.reactivex.rxjava3.core.Single
import retrofit2.Response


class UserRemoteDataSourceImpl(private val retrofitHelper: RetrofitHelper) :
    UserRemoteDataSource {

    //유저 검색
    override fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): Single<SearchedUsers> {
        return retrofitHelper.apiServices.searchUsers(query, page, perPage).map {
            toDataModel(it)
        }

    }

}