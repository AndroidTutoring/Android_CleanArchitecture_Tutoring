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


        binding.user = item
        //data change 요구!
        binding.executePendingBindings()

        item.name = intent
            .getStringExtra(
                "name"
            ).toString()

        item.id = intent
            .getStringExtra(
                "id"
            ).toString()

        item.date = intent
            .getStringExtra(
                "date"
            ).toString()

        item.url = intent
            .getStringExtra(
                "url"
            ).toString()




    }

}