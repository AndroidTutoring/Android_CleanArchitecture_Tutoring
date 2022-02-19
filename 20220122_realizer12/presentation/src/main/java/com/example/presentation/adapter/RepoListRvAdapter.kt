package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.presentation.R
import com.example.presentation.databinding.ItemRepoInfoBinding
import com.example.presentation.model.PresentationUserRepo
import com.example.presentation.viewholder.RepoInfoViewHolder

class RepoListRvAdapter : ListAdapter<PresentationUserRepo, RepoInfoViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoInfoViewHolder {
        val binding: ItemRepoInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_repo_info,
            parent,
            false
        )
        return RepoInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoInfoViewHolder, position: Int) {
        holder.apply {
            bind(currentList[position])
            binding.executePendingBindings()
        }
    }

    override fun getItemCount() = currentList.size


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PresentationUserRepo>() {
            override fun areContentsTheSame(
                oldItem: PresentationUserRepo,
                newItem: PresentationUserRepo
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: PresentationUserRepo,
                newItem: PresentationUserRepo
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}