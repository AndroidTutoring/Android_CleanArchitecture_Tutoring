package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.presentation.R
import com.example.presentation.databinding.ItemRepoUserBinding
import com.example.presentation.model.SearchedUserPresentationModel
import com.example.presentation.viewholder.UserViewHolder

class UserListRvAdapter : ListAdapter<SearchedUserPresentationModel, UserViewHolder>(diffUtil) {

    private var onItemClickListener: ItemClickListener? = null
    private var onFavoriteMarkClickListener: FavoriteClickListener? = null

    //아이템 전체 클릭
    interface ItemClickListener {
        fun onItemClickListener(searchedUser: SearchedUserPresentationModel)//아이템 클릭시 -> 디테일 화면으로?
    }

    //즐겨찾기  클릭
    interface FavoriteClickListener {
        fun onFavoriteMarkListener(searchedUser: SearchedUserPresentationModel, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: ItemRepoUserBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_repo_user,
                parent,
                false
            )
        return UserViewHolder(binding)
    }


    //아이템 전체 클릭
    fun setItemClickListener(itemCliCkListener: ItemClickListener) {
        this.onItemClickListener = itemCliCkListener
    }

    //즐겨찾기 버튼 클릭
    fun setFavoriteMarkClickListener(favoriteClickListener: FavoriteClickListener) {
        this.onFavoriteMarkClickListener = favoriteClickListener
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.apply {
            bind(currentList[adapterPosition])

            //즐겨찾기 버튼 클릭시
            binding.ivBtnFavorite.setOnClickListener {
                onFavoriteMarkClickListener?.onFavoriteMarkListener(
                    currentList[adapterPosition],
                    adapterPosition
                )
            }

            //아이템 클릭시
            binding.root.setOnClickListener {
                onItemClickListener?.onItemClickListener(currentList[adapterPosition])
            }

            binding.executePendingBindings()
        }
    }


    override fun getItemCount() = currentList.size


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SearchedUserPresentationModel>() {
            override fun areContentsTheSame(
                oldItem: SearchedUserPresentationModel,
                newItem: SearchedUserPresentationModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: SearchedUserPresentationModel,
                newItem: SearchedUserPresentationModel
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}