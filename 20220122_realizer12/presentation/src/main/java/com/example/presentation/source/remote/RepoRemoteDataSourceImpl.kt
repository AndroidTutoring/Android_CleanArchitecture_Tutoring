package com.example.presentation.source.remote

import com.example.presentation.model.UserRepo
import com.example.presentation.retrofit.RetrofitHelper
import io.reactivex.rxjava3.core.Single

class RepoRemoteDataSourceImpl(private val retrofitHelper: RetrofitHelper) : RepoRemoteDataSource {

    //해당 유저의  repository 검색
    override fun getUserRepoList(
        userName: String
    ): Single<List<UserRepo>> {
        return retrofitHelper.apiServices.getUserRepoInfo(userName)
    }

}
