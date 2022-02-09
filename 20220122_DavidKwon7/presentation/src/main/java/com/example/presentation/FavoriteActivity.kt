package com.example.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.repository.githubRepository.GithubRepository
import com.example.presentation.databinding.ActivityFavoriteBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class FavoriteActivity : AppCompatActivity() {

    private var backPressedTime : Long = 0
    lateinit var binding: ActivityFavoriteBinding
    lateinit var repository : GithubRepository
    lateinit var adapter: FavoriteAdapter

    private val backButtonSubject : Subject<Long> =
        BehaviorSubject.createDefault(0L)

    private lateinit var compositeDisposable: CompositeDisposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickFavorite()
        setAdapter()
        itemDeleteClick()
        back2()
        initVMFactory()

    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }



    @SuppressLint("CheckResult")
    private fun setAdapter(){
        binding.rvProfile.apply {
            adapter = this.adapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }

        repository.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { data ->
                adapter.postList = data
                adapter.notifyDataSetChanged()
            }


    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - backPressedTime <2000){
            finish()
            return
        }
        Toast.makeText(this, "한 번 더 뒤로가기 버튼을 눌러주세요", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }

    private fun clickFavorite() {
            binding.btn1.setOnClickListener {
                val intent = Intent(
                    this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    //item click
    private fun itemDeleteClick() {
        adapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(v: View, data: User, pos: Int) {
                repository.deleteFav(deleteUser = User(
                    name=String(),
                    id= String(),
                    date = String(),
                    url = String() ))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        })
    }
    @SuppressLint("CheckResult")
    private fun back2() {
        backButtonSubject.buffer(2,1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t ->
                if (t[1] - t[0] <= 1500) {
                    finish()
                } else {
                    Toast.makeText(this, "뒤로가기 한 번 더 누르면 꺼짐 ", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun initVMFactory(){
        val viewModelFactory = ViewModelProvider(
            this,ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)
    }


}