package com.example.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.data.model.UserDataModel
import com.example.presentation.databinding.ActivityProfileDetailBinding

class ProfileDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileDetailBinding
    lateinit var item : UserDataModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.user = item
        //data change 요구!
        binding.executePendingBindings()


        val getName = intent.getStringExtra("name")
        val getId = intent.getStringExtra("id")
        val getDate = intent.getStringExtra("date")
        val getUrl = intent.getStringExtra("url")

        val name : TextView = binding.tvRvName
        val id : TextView = binding.tvRvId
        val date : TextView = binding.createdTime
        val url :TextView = binding.htmlUrl

        name.text = getName
        id.text = getId
        date.text = getDate
        url.text = getUrl



    }

}