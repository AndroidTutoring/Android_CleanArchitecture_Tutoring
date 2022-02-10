package com.example.data.repository

import com.example.data.source.remote.RepoRemoteDataSource

class RepoRepositoryImpl(
    private val repoRemoteDataSource: RepoRemoteDataSource
) : RepoRepository {

    override fun getUserRepoList(
        userName: String
    ) = repoRemoteDataSource.getUserRepoList(userName)

}