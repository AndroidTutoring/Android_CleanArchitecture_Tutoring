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
    ): Single<ArrayList<UserRepo>> {
       return RetrofitHelper.apiServices.getUserRepoInfo(userName)
    }

}

//.enqueue(object : Callback<ArrayList<UserRepo>> {
//    override fun onResponse(
//        call: Call<ArrayList<UserRepo>>,
//        response: Response<ArrayList<UserRepo>>
//    ) {
//        if(response.isSuccessful){
//            onSuccess(response.body())
//        }
//    }
//    override fun onFailure(call: Call<ArrayList<UserRepo>>, t: Throwable) {
//        onFailure(t)
//    }
//})