package com.example.data.source.remote

import com.example.data.model.UserRepoDataModel
import io.reactivex.rxjava3.core.Single

interface RepoRemoteDataSource {
    fun getUserRepoList(
        userName: String
    ): Single<List<UserRepoDataModel>>
}