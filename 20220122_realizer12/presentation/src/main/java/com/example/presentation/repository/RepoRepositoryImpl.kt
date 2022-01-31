package com.example.presentation.repository

import com.example.presentation.model.UserRepo
import com.example.presentation.source.remote.RepoRemoteDataSource
import com.example.presentation.source.remote.UserRemoteDataSource

class RepoRepositoryImpl(
    private val repoRemoteDataSource: RepoRemoteDataSource
):RepoRepository {

    override fun getUserRepoList(
        userName: String,
        onSuccess: (ArrayList<UserRepo>?) -> Unit,
        onFailure: (t: Throwable) -> Unit
    ) {
        repoRemoteDataSource.getUserRepoList(userName, onSuccess, onFailure)
    }

}