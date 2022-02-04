package com.example.recylcerviewtest01.githubSource.remote

import com.example.presentation.User
import io.reactivex.Completable
import io.reactivex.Single

interface RemoteDataSource {
    fun getRepos() : Single<List<User>>


}