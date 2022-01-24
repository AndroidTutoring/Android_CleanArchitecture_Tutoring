package com.example.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.presentation.fragment.FavoriteFragment
import com.example.presentation.fragment.UserFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> UserFragment()//첫번째는  유저 프래그먼트
            else -> FavoriteFragment()//두번째는  즐겨찾기 프래그먼트
        }
    }

    //유저, 즐겨찾기 두개니까  2로 고정 시킴.
    override fun getItemCount(): Int  =2
}