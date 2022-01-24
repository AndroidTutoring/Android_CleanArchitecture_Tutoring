package com.example.presentation.activity

import android.os.Bundle
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it) }) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}