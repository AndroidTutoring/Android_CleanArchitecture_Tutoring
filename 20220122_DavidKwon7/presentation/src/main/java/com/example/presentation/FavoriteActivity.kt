package com.example.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.repository.githubRepository.GithubRepository
import com.example.presentation.databinding.ActivityFavoriteBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.item_recycler_ex.*

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
        itemDeleteClick()
        initVMFactory()
        connectVM()

    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun connectVM(){
        val viewModelFactory = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory())
            .get(MainViewModel::class.java)

        viewModelFactory.liveData.observe(this, Observer {
            tv_rv_id.text = it.id
            tv_rv_name.text = it.name
            created_time.text = it.date
            html_url.text = it.url
        })
    }


    private fun clickFavorite() {
            binding.btn1.setOnClickListener {
                binding.btn1.setOnClickListener {
                    startActivity(Intent(this,MainActivity::class.java))
                }
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
    private fun initVMFactory(){
        val viewModelFactory = ViewModelProvider(
            this,ViewModelProvider.NewInstanceFactory())
            .get(FavoriteViewModel::class.java)


    }


}