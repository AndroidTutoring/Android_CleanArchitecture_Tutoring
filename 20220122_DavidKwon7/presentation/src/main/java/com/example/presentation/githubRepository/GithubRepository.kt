package com.example.recylcerviewtest01.githubRepository

import com.example.presentation.User
import com.example.recylcerviewtest01.githubSource.remote.RemoteDataSource
import com.example.recylcerviewtest01.githubSource.remote.RemoteDataSourceImpl
import io.reactivex.Completable
import io.reactivex.Single

interface GithubRepository {

    fun getCachedUserList() : Single<List<User>>
    fun addFav()  : Completable
    fun deleteFav() : Completable

    fun getRepos() : Single<List<User>>

}