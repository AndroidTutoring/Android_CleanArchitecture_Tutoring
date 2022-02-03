package com.example.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProfileDetailActivity : AppCompatActivity() {

    lateinit var datas : List<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)

        val getName = intent.getStringExtra("name")
        val getId = intent.getStringExtra("id")
        val getDate = intent.getStringExtra("date")
        val getUrl = intent.getStringExtra("url")

        val name : TextView = findViewById(R.id.tv_rv_name)
        val id : TextView = findViewById(R.id.tv_rv_id)
        val date : TextView = findViewById(R.id.created_time)
        val url :TextView = findViewById(R.id.html_url)

        name.text = getName
        id.text = getId
        date.text = getDate
        url.text = getUrl



    }

}