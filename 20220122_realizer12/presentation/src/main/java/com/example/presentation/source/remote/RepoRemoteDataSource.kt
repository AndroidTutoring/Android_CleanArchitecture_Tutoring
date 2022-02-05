package com.example.presentation.source.remote

import com.example.presentation.model.UserRepo
import io.reactivex.rxjava3.core.Single

interface RepoRemoteDataSource {
    fun getUserRepoList(
        userName: String
    ): Single<List<UserRepo>>
}