package com.example.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.UserDataModel
import com.example.presentation.databinding.ItemRecyclerExBinding

class FavoriteAdapter(val context: Context) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    var postList = listOf<UserDataModel>()

    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) :
            ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ItemRecyclerExBinding.inflate(inflater,parent,false)
        return ViewHolder(listItemBinding)
    }

    class ViewHolder (val binding: ItemRecyclerExBinding) : RecyclerView.ViewHolder(binding.root) {
        private var listener : OnItemClickListener? =null


        fun bind(item: UserDataModel) {
            binding.user = item
            binding.executePendingBindings()

            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION){
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                }
            }
        }
    }


    override fun getItemCount(): Int {
            return postList.size
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }

    fun addItem(list: List<UserDataModel>) {
        val items = ArrayList<UserDataModel>()
        items.clear()
        items.addAll(list)
    }
}
