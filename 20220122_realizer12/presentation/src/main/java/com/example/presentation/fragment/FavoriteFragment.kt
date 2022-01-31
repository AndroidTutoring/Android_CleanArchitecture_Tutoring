package com.example.presentation.fragment

import android.os.Bundle
import android.view.View
import com.example.presentation.adapter.UserListRcyAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentFavoriteBinding
import com.example.presentation.model.SearchedUser
import com.example.presentation.repository.UserRepository
import com.example.presentation.repository.UserRepositoryImpl
import com.example.presentation.room.FavoriteMarkDataBase
import com.example.presentation.source.local.UserLocalDataSourceImpl
import com.example.presentation.source.remote.UserRemoteDataSourceImpl
import timber.log.Timber

//즐겨찾기 프래그먼트
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private lateinit var favoriteMarkedRcyAdapter: UserListRcyAdapter


    private val userRepository: UserRepository by lazy {
        val favoriteMarkDataBase = FavoriteMarkDataBase.getInstance(requireContext().applicationContext)
        val remoteDataSource = UserRemoteDataSourceImpl()
        val localDataSource = UserLocalDataSourceImpl(favoriteMarkDataBase?.getFavoriteMarkDao())
        UserRepositoryImpl(localDataSource,remoteDataSource)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSet()
        listenerEvent()
    }

    override fun onResume() {
        super.onResume()
        getFavoriteGitUsers()
    }

    //리스너 기능 모음.
    private fun listenerEvent(){
        favoriteMarkedRcyAdapter.setFavoriteMarkClickListener(object:UserListRcyAdapter.FavoriteClickListener{
            override fun onFavoriteMarkListener(searchedUser: SearchedUser, position: Int) {

                userRepository.deleteFavoriteUser(searchedUser)
                val newList= favoriteMarkedRcyAdapter.currentList.toMutableList()
                newList.removeAll { it.id == searchedUser.id }
                favoriteMarkedRcyAdapter.submitList(newList.toList())

            }
        })
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
        favoriteMarkedRcyAdapter.submitList(userRepository.getFavoriteUsers())
    }
}