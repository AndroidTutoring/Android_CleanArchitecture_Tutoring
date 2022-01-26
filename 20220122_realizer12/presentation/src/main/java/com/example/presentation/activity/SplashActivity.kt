package com.example.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.presentation.base.BaseActivity
import com.example.presentation.databinding.ActivitySplashBinding

class SplashActivity:BaseActivity<ActivitySplashBinding>({ ActivitySplashBinding.inflate(it) }) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            gotoMainActivity()
        },2000)//2초뒤에 메인으로
    }

    //실험실 가기
    private fun gotoMainActivity(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}