package com.example.presentation.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.SearchedUser
import com.example.presentation.adapter.UserListRvAdapter
import com.example.presentation.model.PresentationSearchedUser


//바인딩 어뎁터 모음
@BindingAdapter("bind:addList")
fun RecyclerView.addList(list: List<PresentationSearchedUser>){
   (this.adapter as UserListRvAdapter).submitList(list)
}