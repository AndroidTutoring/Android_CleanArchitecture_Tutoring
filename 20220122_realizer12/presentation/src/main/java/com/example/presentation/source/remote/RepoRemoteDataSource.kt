package com.example.presentation.source.remote

import com.example.presentation.model.SearchedUsers
import com.example.presentation.model.UserRepo
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback

interface RepoRemoteDataSource {
    fun getUserRepoList(
        userName:String
    ): Single<ArrayList<UserRepo>>
}