package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.presentation.databinding.ItemRepoInfoBinding
import com.example.data.model.UserRepo
import com.example.presentation.viewholder.RepoInfoViewHolder

class RepoListRvAdapter : ListAdapter<UserRepo, RepoInfoViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoInfoViewHolder {
        val binding =
            ItemRepoInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoInfoViewHolder, position: Int) {
        holder.apply {
            bind(currentList[position])
        }
    }

    override fun getItemCount() = currentList.size


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<UserRepo>() {
            override fun areContentsTheSame(oldItem: UserRepo, newItem: UserRepo): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: UserRepo, newItem: UserRepo): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}