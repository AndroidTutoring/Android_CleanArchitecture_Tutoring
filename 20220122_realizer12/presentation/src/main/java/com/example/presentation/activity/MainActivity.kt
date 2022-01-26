package com.example.presentation.activity

import android.os.Bundle
import com.example.presentation.adapter.MainViewPagerAdapter
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSet()
    }


    //초기 뷰 세팅
    fun initSet(){

       //메인 뷰페이져  FragmentStateAdapter 연결
       binding.vpMain.apply {
           this.adapter = MainViewPagerAdapter(this@MainActivity)
       }

       //tablayout , 뷰페이저  연결  및 각 탭 구성
       TabLayoutMediator(binding.tabMain, binding.vpMain){ tab, position ->
           when(position){
               0->tab.text = "유저"
               1->tab.text = "즐겨찾기"
           }
       }.attach()

    }
}