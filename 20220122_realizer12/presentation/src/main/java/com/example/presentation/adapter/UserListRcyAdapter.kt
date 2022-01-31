package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemRepoUserBinding
import com.example.presentation.model.SearchedUser
import com.example.presentation.viewholder.UserViewHolder
import timber.log.Timber

class UserListRcyAdapter:ListAdapter<SearchedUser,UserViewHolder> (diffUtil){

    private var onItemClickListener: ItemCliCkListener? = null
    private var onFavoriteMarkClickListener: FavoriteClickListener? = null

    //아이템 전체 클릭
    interface ItemCliCkListener{
        fun onItemClickListener(searchedUser: SearchedUser)//아이템 클릭시 -> 디테일 화면으로?
    }

    //즐겨찾기  클릭
    interface FavoriteClickListener{
        fun onFavoriteMarkListener(searchedUser: SearchedUser,position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
      val binding = ItemRepoUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }


    //아이템 전체 클릭
    fun setItemClickListener(itemCliCkListener: ItemCliCkListener){
        this.onItemClickListener = itemCliCkListener
    }

    //즐겨찾기 버튼 클릭
    fun setFavoriteMarkClickListener(favoriteClickListener: FavoriteClickListener){
        this.onFavoriteMarkClickListener =  favoriteClickListener
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.apply {
           bind(currentList[adapterPosition])

           //즐겨찾기 버튼 클릭시
           binding.imgBtnFavorite.setOnClickListener {
               onFavoriteMarkClickListener?.onFavoriteMarkListener(currentList[adapterPosition],adapterPosition)
           }

           //아이템 클릭시
           binding.root.setOnClickListener {
               onItemClickListener?.onItemClickListener(currentList[adapterPosition])
           }
       }
    }

    override fun getItemCount() = currentList.size


    companion object{
        val diffUtil=object: DiffUtil.ItemCallback<SearchedUser>(){
            override fun areContentsTheSame(oldItem: SearchedUser, newItem: SearchedUser):Boolean{
                return oldItem == newItem
            }
            override fun areItemsTheSame(oldItem: SearchedUser, newItem: SearchedUser):Boolean{
                return oldItem.id == newItem.id
            }
        }
    }
}