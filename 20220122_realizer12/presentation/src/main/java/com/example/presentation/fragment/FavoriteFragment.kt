package com.example.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.presentation.adapter.UserListRvAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentFavoriteBinding
import com.example.data.model.SearchedUser
import com.example.data.repository.UserRepository
import com.example.data.repository.UserRepositoryImpl
import com.example.data.retrofit.RetrofitHelper
import com.example.data.room.LocalDataBase
import com.example.data.source.local.UserLocalDataSourceImpl
import com.example.data.source.remote.UserRemoteDataSourceImpl
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.viewmodel.MainViewModel
import com.example.presentation.viewmodel.factory.ViewModelFactory

//즐겨찾기 프래그먼트
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    private lateinit var favoriteMarkedRvAdapter: UserListRvAdapter

    //메인 activity와 공유하는  뷰모델
    private val mainSharedViewModel: MainViewModel by lazy {
        ViewModelProvider(
            requireActivity(),
            ViewModelFactory(userRepository)
        ).get(MainViewModel::class.java)
    }

    private val userRepository: UserRepository by lazy {
        val favoriteMarkDataBase = LocalDataBase.getInstance(requireContext().applicationContext)
        val remoteDataSource = UserRemoteDataSourceImpl(RetrofitHelper)
        val localDataSource = UserLocalDataSourceImpl(favoriteMarkDataBase.getFavoriteMarkDao())
        UserRepositoryImpl(localDataSource, remoteDataSource)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSet()
        getDataFromViewModel()
        listenerEvent()
    }

    //뷰모델로부터  데이터 받아옴
    private fun getDataFromViewModel(){
        //즐겨 찾기 유저 리스트 업데이트
        mainSharedViewModel.favoriteFragmentUpdateUserList.subscribe({
            favoriteMarkedRvAdapter.submitList(it as MutableList<PresentationSearchedUser>?)
        },{
            showToast(it.message.toString())
        })
    }


    //리스너 기능 모음.
    private fun listenerEvent() {
        favoriteMarkedRvAdapter.setFavoriteMarkClickListener(object :
            UserListRvAdapter.FavoriteClickListener {
            override fun onFavoriteMarkListener(searchedUser: PresentationSearchedUser, position: Int) {
                mainSharedViewModel.deleteFavoriteUsers(
                    presentationSearchedUser = searchedUser,
                    presentationSearchedUserList = favoriteMarkedRvAdapter.currentList,
                    shouldRemoveData = true)
            }
        })
    }

    //초기세팅
    private fun initSet() {

        //리사이클러뷰 세팅
        favoriteMarkedRvAdapter = UserListRvAdapter()
        binding.rcyFavoriteList.apply {
            adapter = favoriteMarkedRvAdapter
        }

        getFavoriteGitUsers()
    }

    //즐겨찾기한 유저들 가져옴.
    private fun getFavoriteGitUsers() {
        mainSharedViewModel.getFavoriteUsers()
    }
}