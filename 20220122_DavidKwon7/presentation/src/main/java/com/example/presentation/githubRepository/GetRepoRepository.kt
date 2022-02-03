package com.example.recylcerviewtest01.githubRepository

import android.database.Observable
import com.example.presentation.User
import com.example.recylcerviewtest01.githubSource.remote.RemoteDataSource
import com.example.recylcerviewtest01.githubSource.remote.RemoteDataSourceImpl
import io.reactivex.Single

class GetRepoRepository {
    val getRepoRemoteDataSource : RemoteDataSource = RemoteDataSourceImpl()

    fun getRepos() : Single<List<User>> = getRepoRemoteDataSource.getRepos()
}