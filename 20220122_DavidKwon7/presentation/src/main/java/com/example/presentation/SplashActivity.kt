package com.example.presentation
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.presentation.databinding.ActivitySplashBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding?=null

    private val disposables : CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val backButtonSubject: BehaviorSubject<Long> =
        BehaviorSubject
            .createDefault(0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initTimer()
        backSetting()
    }

    override fun onDestroy() {
        disposables?.clear()
        super.onDestroy()
    }

    //timer, zip
    @SuppressLint("CheckResult")
    private fun initTimer() {
        val intent = Intent(this, MainActivity::class.java)

        Observable.timer(2000L, TimeUnit.MILLISECONDS)
            .subscribe()
        startActivity(intent)
    }


    @SuppressLint("CheckResult")
    private fun backSetting() {
        backButtonSubject
            .buffer(2, 1)
            .map { it[1] - it[0] < 1500 }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { willFinish ->
                if (willFinish) finish()
                else Toast.makeText(
                    this,
                    "다시 한 번 더 눌러주세요",
                    Toast.LENGTH_SHORT
                ).show()
            } .addTo(disposables)
            }






    }

