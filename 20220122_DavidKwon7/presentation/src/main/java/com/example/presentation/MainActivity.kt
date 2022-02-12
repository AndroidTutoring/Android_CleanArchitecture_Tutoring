package com.example.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.githubRepository.GithubRepository
import com.example.data.repository.githubRepository.GithubRepositoryImpl
import com.example.data.repository.githubSource.local.LocalDataSourceImpl
import com.example.presentation.databinding.ActivityMainBinding
import com.example.data.repository.githubSource.remote.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class MainActivity : AppCompatActivity()  {

    private val githubRepos: ArrayList<User> = ArrayList()

    private val listData = listOf<User>()
    private val position: Int?=null
    private lateinit var api: Api
    private lateinit var userdao: UserDao
    private lateinit var binding: ActivityMainBinding
    private val compositeDisposable : CompositeDisposable by lazy {
        CompositeDisposable()
    }
    val viewModelFactory = ViewModelProvider(
        this, ViewModelProvider.NewInstanceFactory())
        .get(MainViewModel::class.java)
    private val backButtonSubject : Subject<Long> =
        BehaviorSubject.createDefault(0L)
    lateinit var repository : GithubRepository
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
        back2()
        getDataFromVM()
    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
    fun getDataFromVM(){
        viewModelFactory.publishSubject.subscribe{
            adapter.addNewItem(it)
        }
    }

        private fun clickFavorite() {
            binding.btn2.setOnClickListener {
                startActivity(Intent(this,FavoriteActivity::class.java))
            }
        }

    //item click
    private fun itemFavClick() {
        adapter.setOnItemClickListener(object :
            OnItemClickListener {
            override fun onItemClick(v: View, data: User, pos: Int) {
                repository.deleteFav(
                    deleteUser = User(
                    name=String(),id= String(),date = String(),url = String() ))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            }
        })
    }
    //뒤로 가기 ~ 질문 하기
    @SuppressLint("CheckResult")
    private fun back2() {
        backButtonSubject.buffer(2,1)
            .observeOn(AndroidSchedulers.mainThread())
            .map{t ->
                t[1] - t[0] < 1500L
            }
            .subscribe { willFinish ->
                if (willFinish){
                    finish()
                } else{
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                }
            }

    }


}