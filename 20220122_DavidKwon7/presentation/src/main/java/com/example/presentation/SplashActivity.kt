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
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.lang.Exception
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var disposables: CompositeDisposable

    private val backPressedDisposable: Disposable? = null
    private var backPressedSubject = BehaviorSubject.createDefault(0L)
    private val backButtonSubject: Subject<Long> = BehaviorSubject.createDefault(0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initTimer()
        back2()


    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
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
    private fun back2() {
        backButtonSubject.buffer(2, 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                if (t[1] - t[0] <= 1500) {
                    finish()
                } else {
                    Toast.makeText(this, "뒤로가기 한 번 더 누르면 꺼짐 ", Toast.LENGTH_SHORT).show()
                }
            }

    }


}