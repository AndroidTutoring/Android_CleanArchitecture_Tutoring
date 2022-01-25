package com.example.presentation.util

import retrofit2.Call
import retrofit2.Callback

object Util {

    //retrofit enqueue -> 다시 콜
    fun <T> retryCall(call:Call<T>, callback: Callback<T>){
        call.clone().enqueue(callback)
    }

}

