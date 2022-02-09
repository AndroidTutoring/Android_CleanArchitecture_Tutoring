package com.example.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.data.repository.githubRepository.GithubRepository
import com.example.data.repository.githubRepository.GithubRepositoryImpl
import com.example.data.repository.githubSource.local.LocalDataSourceImpl
import com.example.presentation.databinding.ActivityMainBinding
import com.example.data.repository.githubSource.remote.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class MainActivity : AppCompatActivity()  {

    private val githubRepos: ArrayList<User> = ArrayList()

    private val listData = listOf<User>()
    private val position: Int?=null
    private lateinit var api: Api
    private lateinit var userdao: UserDao
    private lateinit var binding: ActivityMainBinding
    private val disposables : CompositeDisposable by lazy {
        CompositeDisposable()
    }
    lateinit var repository : GithubRepository
    lateinit var mainViewModel : MainViewModel
    private val adapter: ProfileAdapter by lazy {
        ProfileAdapter(listData,this)
    }
    private val localDataSource = LocalDataSourceImpl(dao = userdao)
    private val remoteDataSource = RemoteDataSourceImpl(api)
    private val githubRepository = GithubRepositoryImpl(localDataSource = localDataSource,
    remoteDataSource = remoteDataSource)

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickFavorite()
        itemFavClick()
        backFromVM()

    }
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }


        private fun clickFavorite() {
            binding.btn2.setOnClickListener {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }

    //item click
    private fun itemFavClick() {
        adapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(v: View, data: User, pos: Int) {
                repository.deleteFav(deleteUser = User(
                    name=String(),id= String(),date = String(),url = String() ))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun backFromVM(){
        mainViewModel.publishSubject.subscribe{ it ->
            if (true){
                super.onBackPressed()
            }  else{
                Log.d(this.toString(), "backFromVM: 에러.")
            }
            
        }
    }


}