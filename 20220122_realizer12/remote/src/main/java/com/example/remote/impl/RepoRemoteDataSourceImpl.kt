package com.example.remote.impl

import com.example.data.model.UserRepo
import com.example.data.source.remote.RepoRemoteDataSource
import com.example.remote.model.UserRepoRemote.Companion.toDataModel
import com.example.remote.retrofit.ApiService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RepoRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    RepoRemoteDataSource {

    //해당 유저의  repository 검색
    override fun getUserRepoList(
        userName: String
    ): Single<List<UserRepo>> {
        return apiService.getUserRepoInfo(userName).map {
            it.map { userRepoRemote -> toDataModel(userRepoRemote) }
        }
    }

}
