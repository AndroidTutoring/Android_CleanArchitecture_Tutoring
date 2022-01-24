package com.example.presentation.fragment

import android.os.Bundle
import android.view.View
import com.example.presentation.base.BaseFragment
import com.example.presentation.databinding.FragmentUserBinding

//유저 프래그먼트
class UserFragment:BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}