package com.example.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.ItemRepoUserBinding
import com.example.presentation.model.SearchedUser
import com.example.presentation.viewholder.UserViewHolder

class UserListRcyAdapter:ListAdapter<SearchedUser,UserViewHolder> (diffUtil){

    private var onItemClickListener: ItemCliCkListener? = null

    interface ItemCliCkListener{
        fun onItemClickListener(searchedUser: SearchedUser)//아이템 클릭시 -> 디테일 화면으로?
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
      val binding = ItemRepoUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    fun setItemClickListener(itemCliCkListener: ItemCliCkListener){
        this.onItemClickListener = itemCliCkListener
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       holder.apply {
           bind(currentList[position])

           //아이템 클릭시
           itemView.setOnClickListener {
               onItemClickListener?.onItemClickListener(currentList[position])
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