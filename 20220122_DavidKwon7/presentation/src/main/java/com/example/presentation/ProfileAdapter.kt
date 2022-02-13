package com.example.presentation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.UserDataModel
import com.example.presentation.databinding.ItemRecyclerExBinding


class ProfileAdapter(var postList: List<UserDataModel>, val context: MainActivity) :
RecyclerView.Adapter<ProfileAdapter.ViewHolder>(){
    lateinit var binding : ItemRecyclerExBinding
    private val githubRepos: ArrayList<UserDataModel> = ArrayList()

    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener:OnItemClickListener) {
        this.listener = listener
        notifyDataSetChanged()
    }


     class ViewHolder(
         private val binding: ItemRecyclerExBinding
     ) : RecyclerView.ViewHolder(binding.root) {
         private var listener : OnItemClickListener? = null
        private val txtName: TextView = itemView.findViewById(R.id.tv_rv_name)
        private val txtId: TextView = itemView.findViewById(R.id.tv_rv_id)
        private val txtTime : TextView = itemView.findViewById(R.id.created_time)
        private val txtUrl : TextView = itemView.findViewById(R.id.html_url)


        fun bind(item: UserDataModel) {
            //삭제 예정
            txtName.text = item.name
            txtId.text = item.id
            txtTime.text = item.date
            txtUrl.text = item.url

            //data change 요구!
            binding.executePendingBindings()


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
        return ViewHolder(binding)
    }

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
    fun addNewItem(itemsNew: List<UserDataModel>){
        val items = ArrayList<UserDataModel>()
        items.clear() // ->> optional if you need have clear of object
        items.addAll(itemsNew)
        notifyDataSetChanged()
    }

}

