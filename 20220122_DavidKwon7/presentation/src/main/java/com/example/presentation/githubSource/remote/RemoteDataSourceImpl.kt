package com.example.recylcerviewtest01.githubSource.remote

import com.example.presentation.GithubRemoteRepoImpl
import com.example.presentation.User
import io.reactivex.Single
class RemoteDataSourceImpl :RemoteDataSource {

    val api = GithubRemoteRepoImpl.service

    override fun getRepos(): Single<List<User>> {
        return api.getRepos("tkdgusl94")

    }

}