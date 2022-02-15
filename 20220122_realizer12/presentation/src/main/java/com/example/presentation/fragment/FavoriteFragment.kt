package com.example.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.presentation.R
import com.example.presentation.adapter.UserListRvAdapter
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentFavoriteBinding
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.viewmodel.MainViewModel

//즐겨찾기 프래그먼트
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    private lateinit var favoriteMarkedRvAdapter: UserListRvAdapter

    //메인 activity와 공유하는  뷰모델
    private val mainSharedViewModel: MainViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSet()
        listenerEvent()
    }


    //리스너 기능 모음.
    private fun listenerEvent() {
        favoriteMarkedRvAdapter.setFavoriteMarkClickListener(object :
            UserListRvAdapter.FavoriteClickListener {
            override fun onFavoriteMarkListener(
                searchedUser: PresentationSearchedUser,
                position: Int
            ) {
                mainSharedViewModel.deleteFavoriteUsers(
                    presentationSearchedUser = searchedUser
                )
            }
        })
    }

    //초기세팅
    private fun initSet() {

        binding.thisFragment = this
        binding.lifecycleOwner = this
        binding.mainViewModel = mainSharedViewModel

        //리사이클러뷰 세팅
        favoriteMarkedRvAdapter = UserListRvAdapter()
        binding.rcyFavoriteList.apply {
            adapter = favoriteMarkedRvAdapter
        }

        getFavoriteGitUsers()
    }

    //미리 뷰모델 안에서 세팅 되어있던  즐겨찾기 리스트를 가져와 업데이트 해줌
    private fun getFavoriteGitUsers() {
        favoriteMarkedRvAdapter.submitList(mainSharedViewModel.vmFavoriteUserList?.toMutableList())
    }
}