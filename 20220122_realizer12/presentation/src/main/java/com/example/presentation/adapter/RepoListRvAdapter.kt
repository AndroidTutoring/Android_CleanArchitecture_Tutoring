package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.presentation.R
import com.example.presentation.databinding.ItemRepoInfoBinding
import com.example.presentation.model.UserRepoPresentationModel
import com.example.presentation.viewholder.RepoInfoViewHolder

class RepoListRvAdapter : ListAdapter<UserRepoPresentationModel, RepoInfoViewHolder>(diffUtil) {

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
        val diffUtil = object : DiffUtil.ItemCallback<UserRepoPresentationModel>() {
            override fun areContentsTheSame(
                oldItem: UserRepoPresentationModel,
                newItem: UserRepoPresentationModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: UserRepoPresentationModel,
                newItem: UserRepoPresentationModel
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}