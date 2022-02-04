package com.example.recylcerviewtest01.githubRepository

import android.database.Observable
import com.example.presentation.Api
import com.example.presentation.User
import com.example.recylcerviewtest01.githubSource.remote.RemoteDataSource
import com.example.recylcerviewtest01.githubSource.remote.RemoteDataSourceImpl
import io.reactivex.Single

class GetRepoRepository( api: Api) {
    val getRepoRemoteDataSource : RemoteDataSource = RemoteDataSourceImpl(api )

    fun getRepos() : Single<List<User>> = getRepoRemoteDataSource.getRepos()
}