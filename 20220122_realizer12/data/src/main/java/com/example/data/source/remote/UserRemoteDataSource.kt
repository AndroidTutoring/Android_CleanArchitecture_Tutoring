package com.example.data.source.remote

import com.example.data.model.SearchedUsers
import io.reactivex.rxjava3.core.Single

interface UserRemoteDataSource {
    fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): Single<SearchedUsers>
}