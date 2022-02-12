package com.example.data.repository.githubSource.remote

import com.example.presentation.User
import io.reactivex.Completable
import io.reactivex.Single

interface RemoteDataSource {
    fun getRepos(userName:String) : Single<List<User>>


}