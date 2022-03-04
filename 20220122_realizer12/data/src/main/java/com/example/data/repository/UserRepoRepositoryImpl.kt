package com.example.data.repository

import com.example.data.model.UserRepoDataModel.Companion.toDataModel
import com.example.data.model.UserRepoDataModel.Companion.toEntity
import com.example.data.source.remote.RepoRemoteDataSource
import com.example.domain.repository.UserRepoRepository

class UserRepoRepositoryImpl(
    private val repoRemoteDataSource: RepoRemoteDataSource
) : UserRepoRepository {

    override fun getUserRepoList(
        userName: String
    ) = repoRemoteDataSource.getUserRepoList(userName).map { it.map {userRepo-> toEntity(userRepo) } }

}