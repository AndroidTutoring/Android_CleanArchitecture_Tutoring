package com.example.remote.source

import com.example.remote.retrofit.RetrofitClient
import com.example.data.model.UserDataModel
import com.example.data.repository.githubSource.remote.RemoteDataSource
import io.reactivex.Single

class RemoteDataSourceImpl(
) : RemoteDataSource {
    override fun getRepos(userName: String): Single<List<UserDataModel>> {
        return RetrofitClient.createGithubAPI.getRepos("tkdgusl94")
    }


}