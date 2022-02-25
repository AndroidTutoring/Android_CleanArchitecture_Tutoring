package com.example.data.repository

import com.example.data.source.remote.RepoRemoteDataSource

class UserRepoRepositoryImpl(
    private val repoRemoteDataSource: RepoRemoteDataSource
) : UserRepoRepository {

    override fun getUserRepoList(
        userName: String
    ) = repoRemoteDataSource.getUserRepoList(userName)

}