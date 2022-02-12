package com.example.data.repository.githubSource.remote

import com.example.data.model.User
import com.example.presentation.Api
import io.reactivex.Single

class RemoteDataSourceImpl(
    private val api : Api
) : RemoteDataSource {
    override fun getRepos(userName: String): Single<List<User>> {
        return api.getRepos("tkdgusl94")
    }


}