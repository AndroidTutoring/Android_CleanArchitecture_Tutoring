package com.example.data.source.remote

import com.example.data.model.UserRepo
import io.reactivex.rxjava3.core.Single

interface RepoRemoteDataSource {
    fun getUserRepoList(
        userName: String
    ): Single<List<UserRepo>>
}