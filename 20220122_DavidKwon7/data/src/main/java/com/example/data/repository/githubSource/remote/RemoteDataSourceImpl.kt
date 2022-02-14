package com.example.data.repository.githubSource.remote

import com.example.data.Retrofit.RetrofitClient
import com.example.data.model.UserDataModel
import io.reactivex.Single

class RemoteDataSourceImpl(
) : RemoteDataSource {
    override fun getRepos(userName: String): Single<List<UserDataModel>> {
        return RetrofitClient.createGithubAPI.getRepos("tkdgusl94")
    }


}