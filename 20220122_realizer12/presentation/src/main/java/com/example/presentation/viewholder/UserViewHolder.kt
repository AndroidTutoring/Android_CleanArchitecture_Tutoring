package com.example.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.presentation.databinding.ItemRepoUserBinding
import com.example.presentation.model.SearchedUser

class UserViewHolder(private val binding: ItemRepoUserBinding) :RecyclerView.ViewHolder(binding.root){
    fun bind(searchedUser: SearchedUser){

        Glide.with(itemView.context).load(searchedUser.avatar_url).into(binding.imgUserProfile)//유저 프로필 추가
        binding.tvNickname.text = searchedUser.login//유저 닉네임 추가
        binding.tvUserUrl.text = searchedUser.html_url//유저 repo
    }
}