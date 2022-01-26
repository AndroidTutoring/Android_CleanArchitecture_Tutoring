package com.example.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivitySplashBinding
import com.example.presentation.model.SearchedUser
import com.example.presentation.model.SearchedUsers
import com.example.presentation.retrofit.RetrofitHelper
import com.example.presentation.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity:BaseActivity<ActivitySplashBinding>({ ActivitySplashBinding.inflate(it) }) {

    private var apiRetryCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searUserInfo()
    }


    //유저 검색 -> 내 깃헙 아이디 검색
    private fun searUserInfo() {
        RetrofitHelper.apiServices.searchUsers(
            query = "realizer12",
            page = 1,
            perPage = 10
        ).enqueue(object : Callback<SearchedUsers> {
            override fun onResponse(call: Call<SearchedUsers>, response: Response<SearchedUsers>) {
                if(response.isSuccessful){//응답 성공
                    val items = response.body()?.items

                    Handler(Looper.getMainLooper()).postDelayed({
                        gotoMainActivity(items)
                    },2000)//통신 성공하고  2초뒤에 메인으로

                }else{//응답 실패
                    retryApiCall(call = call,callback = this)
                }
            }
            override fun onFailure(call: Call<SearchedUsers>, t: Throwable) {
                //통신 재시도
                retryApiCall(call = call,callback = this)
            }
        })
    }

    //메인 가기
    private fun gotoMainActivity(searchUsers:ArrayList<SearchedUser>?){
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra(PARAM_INIT_USER_INFO,searchUsers)
        startActivity(intent)
        finish()
    }

    //응답 실패시  3초 간격으로 api call 해줌.
    private fun <T>retryApiCall(call: Call<T>,callback: Callback<T>) {
        ++apiRetryCount
        if (apiRetryCount < 3) {
            Handler(Looper.getMainLooper()).postDelayed({
                //통신 재시도
                Util.retryCall(call = call, callback = callback)
            }, 3000)//3초 간격으로  api call
        }else{
            showToast("유저목록을 가져오는데 실패하였습니다. ")
            apiRetryCount = 0
        }
    }

    companion object{
        const val PARAM_INIT_USER_INFO = "param_init_user_info"
    }

}