package com.example.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemRepoInfoBinding
import com.example.presentation.model.UserRepoPresentationModel

class RepoInfoViewHolder(
    val binding: ItemRepoInfoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(userRepo: UserRepoPresentationModel) {

        //데바용 repo 모델 연결
        binding.userRepoModel = userRepo
    }
}