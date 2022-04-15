package com.example.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemRepoUserBinding
import com.example.presentation.model.SearchedUserPresentationModel

class UserViewHolder(
    val binding: ItemRepoUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(searchedUser: SearchedUserPresentationModel) {
        binding.searchUserModel = searchedUser
    }
}