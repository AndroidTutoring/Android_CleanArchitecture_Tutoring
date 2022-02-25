package com.example.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.data.repository.UserRepository
import com.example.presentation.R
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivitySplashBinding
import com.example.presentation.model.SearchedUserPresentationModel
import com.example.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {


    private val splashViewModel: SplashViewModel by viewModels()


    //뷰모델로부터  데이터 받아옴
    private fun getDataFromViewModel() {

        //검색  유저 리스트 업데이트
        splashViewModel.searchedUsersList.observe(this, Observer {
            gotoMainActivity(it as ArrayList<SearchedUserPresentationModel>?)
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
    private fun gotoMainActivity(searchUsers: ArrayList<SearchedUserPresentationModel>?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(PARAM_INIT_USER_INFO, searchUsers)
        startActivity(intent)
        finish()
    }

    companion object {
        const val PARAM_INIT_USER_INFO = "param_init_user_info"
    }

}