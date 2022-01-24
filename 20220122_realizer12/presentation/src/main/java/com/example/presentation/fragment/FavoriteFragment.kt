package com.example.presentation.fragment

import android.os.Bundle
import android.view.View
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentFavoriteBinding

//즐겨찾기 프래그먼트
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}