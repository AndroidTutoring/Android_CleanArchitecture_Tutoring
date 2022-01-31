package com.example.presentation.source.remote

import com.example.presentation.model.SearchedUsers
import com.example.presentation.retrofit.RetrofitHelper
import com.example.presentation.util.Const.REQUEST_RETRY_CALLBACK
import com.example.presentation.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRemoteDataSourceImpl:UserRemoteDataSource {

    //유저 검색
    override fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int,
        onSuccess: (SearchedUsers?) -> Unit,
        onFailure: (call:Call<SearchedUsers>, callback: Callback<SearchedUsers>,errorCode:Int?) -> Unit,
    ) {
        RetrofitHelper.apiServices.searchUsers(
            query = query,
            page = page,
            perPage = perPage
        ).enqueue(object : Callback<SearchedUsers> {
            override fun onResponse(call: Call<SearchedUsers>, response: Response<SearchedUsers>) {
                if(response.isSuccessful){
                    onSuccess(response.body())
                }else{
                    //succesful이 아니면 errorbody throw
                    onFailure(call,this,-1)
                }
            }
            override fun onFailure(call: Call<SearchedUsers>, t: Throwable) {
                //통신 재시도
                onFailure(call,this,REQUEST_RETRY_CALLBACK)
            }
        })
    }

}