package com.example.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.presentation.R
import com.example.presentation.adapter.MainViewPagerAdapter
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels()

    private fun dataFromViewModel() {

        mainViewModel.isBackPressPossible.observe(this, Observer {isBackPressPossible->
            if (isBackPressPossible) {//뒤로가기 두번
                super.onBackPressed()
            } else {
                showToast("뒤로가기 두번 눌러주세요")
            }
        })

        mainViewModel.error.observe(this, Observer {
            showToast(it.message.toString())
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSet()
        dataFromViewModel()
        mainViewModel.backPressCheck()
    }

    override fun onBackPressed() {
        mainViewModel.behaviorSubject.onNext(System.currentTimeMillis())
    }


    //초기 뷰 세팅
    private fun initSet() {

        //메인 뷰페이져  FragmentStateAdapter 연결
        binding.vpMain.apply {
            this.adapter = MainViewPagerAdapter(this@MainActivity)
        }

        //tablayout , 뷰페이저  연결  및 각 탭 구성
        TabLayoutMediator(binding.tabMain, binding.vpMain) { tab, position ->
            when (position) {
                0 -> tab.text = "유저"
                1 -> tab.text = "즐겨찾기"
            }
        }.attach()

    }
}