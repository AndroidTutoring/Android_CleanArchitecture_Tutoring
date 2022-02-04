package com.example.presentation.source.remote

import android.view.View
import com.example.presentation.model.UserRepo
import com.example.presentation.retrofit.RetrofitHelper
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoRemoteDataSourceImpl:RepoRemoteDataSource {

    //해당 유저의  repository 검색
    override fun getUserRepoList(
        userName: String
    ): Single<List<UserRepo>> {
       return RetrofitHelper.apiServices.getUserRepoInfo(userName)
    }

}
