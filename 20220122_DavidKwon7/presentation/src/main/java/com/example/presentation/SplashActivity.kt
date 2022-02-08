package com.example.presentation
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.presentation.databinding.ActivitySplashBinding
import com.example.presentation.githubRepository.GithubRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private var binding: ActivitySplashBinding?=null
    private var disposables: CompositeDisposable?=null
    private val githubRepository: GithubRepository?=null

    private val backButtonSubject: BehaviorSubject<Long> = BehaviorSubject.createDefault(0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initTimer()
        back2()

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
    private fun back2() {
        /*backButtonSubject.buffer(2, 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                if (t[1] - t[0] <= 1500) {
                    finish()
                } else {
                    Toast.makeText(this, "뒤로가기 한 번 더 누르면 꺼짐 ", Toast.LENGTH_SHORT).show()
                }
            } */






    }
    private fun getgithubRepository() =githubRepository?.getRepos()

    private fun nextPage(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}