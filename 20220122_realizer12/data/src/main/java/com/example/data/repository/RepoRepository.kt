package com.example.data.repository

import com.example.data.model.UserRepoDataModel
import io.reactivex.rxjava3.core.Single

interface RepoRepository {
    fun getUserRepoList(
        userName: String
    ): Single<List<UserRepoDataModel>>
}