package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.presentation.R
import com.example.presentation.databinding.ItemRepoUserBinding
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.viewholder.UserViewHolder

class UserListRvAdapter : ListAdapter<PresentationSearchedUser, UserViewHolder>(diffUtil) {

    private var onItemClickListener: ItemClickListener? = null
    private var onFavoriteMarkClickListener: FavoriteClickListener? = null

    //아이템 전체 클릭
    interface ItemClickListener {
        fun onItemClickListener(searchedUser: PresentationSearchedUser)//아이템 클릭시 -> 디테일 화면으로?
    }

    //즐겨찾기  클릭
    interface FavoriteClickListener {
        fun onFavoriteMarkListener(searchedUser: PresentationSearchedUser, position: Int)
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
            binding.imgBtnFavorite.setOnClickListener {
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
        val diffUtil = object : DiffUtil.ItemCallback<PresentationSearchedUser>() {
            override fun areContentsTheSame(
                oldItem: PresentationSearchedUser,
                newItem: PresentationSearchedUser
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: PresentationSearchedUser,
                newItem: PresentationSearchedUser
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}