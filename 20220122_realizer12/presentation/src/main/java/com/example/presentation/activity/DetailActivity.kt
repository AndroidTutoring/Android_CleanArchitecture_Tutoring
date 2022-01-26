package com.example.presentation.activity

import android.os.Bundle
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityDetailBinding
import com.example.presentation.databinding.ActivitySplashBinding

class DetailActivity:BaseActivity<ActivityDetailBinding>({ ActivityDetailBinding.inflate(it) }) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}