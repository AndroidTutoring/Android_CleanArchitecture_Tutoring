package com.example.presentation.repository

import com.example.presentation.model.UserRepo
import io.reactivex.rxjava3.core.Single

interface RepoRepository {
    fun getUserRepoList(
        userName: String
    ): Single<List<UserRepo>>
}