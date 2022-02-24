package com.example.remotedata.source

import com.example.data.model.UserDataModel
import com.example.data.repository.githubSource.remote.RemoteDataSource
import io.reactivex.Single

class RemoteDataSourceImpl(
) : RemoteDataSource {
    override fun getRepos(userName: String): Single<List<UserDataModel>> {
        return RetrofitClient.createGithubAPI.getRepos("tkdgusl94")
    }


}