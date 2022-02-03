package com.example.recylcerviewtest01.githubSource.remote

import com.example.recylcerviewtest01.User
import io.reactivex.Completable
import io.reactivex.Single

interface RemoteDataSource {

    fun getRepos() : Single<List<User>>


}