package com.example.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemRepoInfoBinding
import com.example.presentation.model.PresentationUserRepo

class RepoInfoViewHolder(
    val binding: ItemRepoInfoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(userRepo: PresentationUserRepo) {

        //데바용 repo 모델 연결
        binding.userRepoModel = userRepo
    }
}