package com.example.presentation

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recylcerviewtest01.githubRepository.GithubRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileAdapter(var postList : List<User>, val context: Context) :
RecyclerView.Adapter<ProfileAdapter.ViewHolder>(){
    private val githubRepos: ArrayList<User> = ArrayList()


     interface OnItemClickListener{
        fun onItemClick(v:View, data: User, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

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
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recycler_ex,parent,false)
        return ViewHolder(view)    }

    override fun onBindViewHolder(holder: ProfileAdapter.ViewHolder, position: Int) {
        holder.bind(postList[position])


        //클릭 이벤트
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context, ProfileDetailActivity::class.java)
            intent.putExtra("name", postList.get(position).name)
            intent.putExtra("id", postList.get(position).id)
            intent.putExtra("date", postList.get(position).date)
            intent.putExtra("url", postList.get(position).url)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }


    }


    override fun getItemCount(): Int {
        return postList.size
    }

    fun update(githubRepos: List<User>) {
        this.githubRepos.clear()
        this.githubRepos.addAll(githubRepos)
        notifyDataSetChanged()
    }

}

