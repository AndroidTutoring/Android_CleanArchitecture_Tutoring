package com.example.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

//베이스 엑티비티 뷰바인딩 적용
open class BaseActivity<VB : ViewBinding>(private val bindingFactory: (LayoutInflater) -> VB) :
    AppCompatActivity() {

    private var mBinding: VB? = null
    val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }

    fun showToast(msg:String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }

}