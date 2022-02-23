package com.example.remote.impl

import com.example.data.model.SearchedUsers
import com.example.data.source.remote.UserRemoteDataSource
import com.example.remote.model.SearchedUsersRemote.Companion.toDataModel
import com.example.remote.retrofit.ApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class UserRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    UserRemoteDataSource {

    //유저 검색
    override fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): Single<SearchedUsers> {
        return apiService.searchUsers(query, page, perPage).map {
            toDataModel(it)
        }

    }

}