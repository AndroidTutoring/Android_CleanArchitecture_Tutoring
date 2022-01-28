package com.example.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.presentation.databinding.ActivitySplashBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTimer()
    }
    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun initTimer(){
        scope.launch {
            //통신 에러 try / catrch는 어떻게 잡지?
            try {
                delay(2000)
                var intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception){
                delay(3000)
                try {
                    delay(3000)
                    var intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }catch (e: Exception){
                    Toast.makeText(this@SplashActivity, "에러 발생", Toast.LENGTH_SHORT).show()
                }

            }



        }
    }
}