package com.example.remote.source

import com.example.data.model.UserDataModel
import com.example.data.repository.githubSource.remote.RemoteDataSource
import com.example.remote.retrofit.GithubAPI
import io.reactivex.Single
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val githubAPI: GithubAPI
) : RemoteDataSource {
    override fun getRepos(userName: String): Single<List<UserDataModel>> {
        return githubAPI.getRepos("tkdgusl94")
    }


}