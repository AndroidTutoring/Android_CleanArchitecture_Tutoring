package com.example.presentation.fragment

import android.os.Bundle
import android.view.View
import com.example.presentation.adapter.UserListRcyAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentFavoriteBinding
import com.example.presentation.room.FavoriteMarkDataBase
import timber.log.Timber

//즐겨찾기 프래그먼트
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private lateinit var favoriteMarkedRcyAdapter: UserListRcyAdapter

    private val favoriteMarkDataBase: FavoriteMarkDataBase? by lazy {
        FavoriteMarkDataBase.getInstance(requireContext().applicationContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSet()
    }

    override fun onResume() {
        super.onResume()
        getFavoriteGitUsers()
    }

    //초기세팅
    private fun initSet(){

        //리사이클러뷰 세팅
       favoriteMarkedRcyAdapter = UserListRcyAdapter()
       binding.rcyFavoriteList.apply {
           adapter = favoriteMarkedRcyAdapter
       }
    }

    //즐겨찾기한 유저들 가져옴.
    private fun getFavoriteGitUsers(){
        favoriteMarkedRcyAdapter.submitList(favoriteMarkDataBase?.getFavoriteMarkDao()?.getFavoriteGitUsers(true))
    }
}