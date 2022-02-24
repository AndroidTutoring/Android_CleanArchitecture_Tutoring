package com.example.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.model.UserDataModel
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
    private lateinit var favoriteViewModel: FavoriteViewModel
    private val listData = listOf<UserDataModel>()


    private val backButtonSubject : Subject<Long> =
        BehaviorSubject.createDefault(0L)

    private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var viewModel: FavoriteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickFavorite()
        itemDeleteClick()
        setAdapter()


        favoriteViewModel.list.observe(this, Observer { it->
            adapter.addItem(it)
        })

    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


    private fun clickFavorite() {
            binding.goToUser.setOnClickListener {
                    startActivity(Intent(
                        this,
                        MainActivity::class.java))
            }
        }
    //item click
    private fun itemDeleteClick() {
        adapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(
                v: View,
                data: UserDataModel,
                pos: Int) {

                repository.deleteFav(
                    deleteUser = UserDataModel(
                    name = "",
                    id = "",
                    date = "",
                    url = ""
                    ))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        })
    }

    private fun setAdapter(){
        binding.rvProfile.apply {
            adapter = FavoriteAdapter(this@FavoriteActivity)
        }
    }
}