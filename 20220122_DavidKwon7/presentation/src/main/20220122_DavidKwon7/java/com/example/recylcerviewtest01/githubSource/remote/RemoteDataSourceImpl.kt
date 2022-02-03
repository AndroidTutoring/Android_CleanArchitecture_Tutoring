package com.example.recylcerviewtest01.githubSource.remote

import com.example.recylcerviewtest01.Api
import com.example.recylcerviewtest01.GithubRemoteRepoImpl
import com.example.recylcerviewtest01.User
import com.example.recylcerviewtest01.createRetrofit
import io.reactivex.Single

class RemoteDataSourceImpl :RemoteDataSource {
    val api = GithubRemoteRepoImpl.service



    override fun getRepos(): Single<List<User>> {
        return api.getRepos("tkdgusl94")

    }

}