package com.example.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemRepoUserBinding
import com.example.presentation.model.PresentationSearchedUser

class UserViewHolder(
    val binding: ItemRepoUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(searchedUser: PresentationSearchedUser) {
        binding.searchUserModel = searchedUser
    }
}