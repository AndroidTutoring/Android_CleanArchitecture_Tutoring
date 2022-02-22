package com.example.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import com.example.remote.retrofit.RetrofitHelper
import com.example.local.room.LocalDataBase
import com.example.local.impl.UserLocalDataSourceImpl
import com.example.remote.impl.UserRemoteDataSourceImpl
import com.example.presentation.R
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivitySplashBinding
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.viewmodel.SplashViewModel
import com.example.presentation.viewmodel.factory.ViewModelFactory

class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {


    private val userRepository: UserRepository by lazy {
        val favoriteMarkDataBase = LocalDataBase.getInstance(this.applicationContext)
        val remoteDataSource = UserRemoteDataSourceImpl(RetrofitHelper)
        val localDataSource = UserLocalDataSourceImpl(favoriteMarkDataBase.getFavoriteMarkDao())
        UserRepositoryImpl(localDataSource, remoteDataSource)
    }

    private val splashViewModel: SplashViewModel by lazy {
        ViewModelProvider(this,
            ViewModelFactory(userRepository))
            .get(SplashViewModel::class.java)
    }


    //뷰모델로부터  데이터 받아옴
    private fun getDataFromViewModel() {

        //검색  유저 리스트 업데이트
        splashViewModel.searchedUsersList.observe(this, Observer {
            gotoMainActivity(it as ArrayList<PresentationSearchedUser>?)
        })

        //에러관련 처리
        splashViewModel.error.observe(this, Observer {
            showToast(it.message.toString())
            finish()//문제 있으면 앱종료
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataFromViewModel()
        splashViewModel.searchUsers()
    }

    //메인 가기
    private fun gotoMainActivity(searchUsers: ArrayList<PresentationSearchedUser>?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(PARAM_INIT_USER_INFO, searchUsers)
        startActivity(intent)
        finish()
    }

    companion object {
        const val PARAM_INIT_USER_INFO = "param_init_user_info"
    }

}