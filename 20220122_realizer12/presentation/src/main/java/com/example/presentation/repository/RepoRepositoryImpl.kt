package com.example.presentation.repository

import com.example.presentation.source.remote.RepoRemoteDataSource

class RepoRepositoryImpl(
    private val repoRemoteDataSource: RepoRemoteDataSource
) : RepoRepository {

    override fun getUserRepoList(
        userName: String
    ) = repoRemoteDataSource.getUserRepoList(userName)

}