package com.example.recylcerviewtest01.githubSource.remote

import com.example.presentation.Api
import com.example.presentation.User
import io.reactivex.Single

class RemoteDataSourceImpl(
    private val api : Api
) :RemoteDataSource {


    override fun getRepos(): Single<List<User>> {
        return api.getRepos("tkdgusl94")

    }

}