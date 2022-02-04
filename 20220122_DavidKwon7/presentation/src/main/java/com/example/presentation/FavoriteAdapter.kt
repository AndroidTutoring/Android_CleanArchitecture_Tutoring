package com.example.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class FavoriteAdapter(var postList: List<User>, val context: Context) :
RecyclerView.Adapter<FavoriteAdapter.ViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(v:View, data : User, pos:Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_ex,parent,false)
        return ViewHolder(view)
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private var listener : OnItemClickListener? =null
        private val txtName: TextView = itemView.findViewById(R.id.tv_rv_name)
        private val txtId: TextView = itemView.findViewById(R.id.tv_rv_id)
        private val txtTime : TextView = itemView.findViewById(R.id.created_time)
        private val txtUrl : TextView = itemView.findViewById(R.id.html_url)

        fun bind(item: User) {
            txtName.text = item.name
            txtId.text = item.id
            txtTime.text = item.date
            txtUrl.text = item.url

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
}
