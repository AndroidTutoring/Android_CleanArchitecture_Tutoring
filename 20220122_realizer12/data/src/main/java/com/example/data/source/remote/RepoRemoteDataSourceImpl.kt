package com.example.data.source.remote

import com.example.data.model.UserRepo
import com.example.data.retrofit.RetrofitHelper
import io.reactivex.rxjava3.core.Single

class RepoRemoteDataSourceImpl(private val retrofitHelper: RetrofitHelper) : RepoRemoteDataSource {

    //해당 유저의  repository 검색
    override fun getUserRepoList(
        userName: String
    ): Single<List<UserRepo>> {
        return retrofitHelper.apiServices.getUserRepoInfo(userName)
    }

}
