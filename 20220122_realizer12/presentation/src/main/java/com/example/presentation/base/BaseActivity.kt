package com.example.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

//베이스 엑티비티 뷰바인딩 적용
open class BaseActivity<VB : ViewBinding>(private val bindingFactory: (LayoutInflater) -> VB) :
    AppCompatActivity() {
    protected val compositeDisposable = CompositeDisposable()
    private var mBinding: VB? = null
    protected val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = bindingFactory(layoutInflater)
        setContentView(binding.root)
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    override fun onDestroy() {
        super.onDestroy()

        //화면 destroy될때  disposable들 모두 clear -> 메모리 누수 방지
        compositeDisposable.dispose()
    }

}