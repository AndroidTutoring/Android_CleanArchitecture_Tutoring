package com.example.data.repository

import com.example.data.model.UserRepo
import io.reactivex.rxjava3.core.Single

interface RepoRepository {
    fun getUserRepoList(
        userName: String
    ): Single<List<UserRepo>>
}