package com.example.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Dao
import com.example.presentation.databinding.ActivityMainBinding
import com.example.recylcerviewtest01.githubRepository.GetRepoRepository
import com.example.recylcerviewtest01.githubRepository.GithubRepository
import com.example.recylcerviewtest01.githubRepository.GithubRepositoryImpl
import com.example.recylcerviewtest01.githubSource.local.LocalDataSource
import com.example.recylcerviewtest01.githubSource.local.LocalDataSourceImpl
import com.example.recylcerviewtest01.githubSource.remote.RemoteDataSource
import com.example.recylcerviewtest01.githubSource.remote.RemoteDataSourceImpl
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import java.sql.Time

class MainActivity : AppCompatActivity() {


    val listData = listOf<User>()


    private lateinit var binding: ActivityMainBinding
    private val disposable : CompositeDisposable by lazy {
        CompositeDisposable()
    }
    lateinit var repository : GithubRepository
    private val adapter: ProfileAdapter by lazy {
        ProfileAdapter(listData,this)
    }
    private val backButtonSubject : Subject<Long> = BehaviorSubject.createDefault(0L)
    val getRepoRepository = GetRepoRepository()

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickFavorite()
        setAdapter()
        back2()
        itemFavClick()




    }
    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }



        private fun clickFavorite() {
            binding.btn2.setOnClickListener {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }


    @SuppressLint("CheckResult")
    private fun setAdapter(){

            disposable.add(getRepoRepository.getRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(2)
                .subscribe{ item ->
                    adapter.update(item)
                }
            )
    }
    //item click
    private fun itemFavClick() {
            adapter.setOnItemClickListener(object :
                ProfileAdapter.OnItemClickListener {
                override fun onItemClick(v: View, data: User, pos: Int) {
                    repository.deleteFav()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()

                }
            })

    }
    //뒤로 가기
    private fun back2() {
        backButtonSubject
            .buffer(2, 1)
            .map { it[1] - it[0] < 1500 }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { willFinish ->
                if (willFinish) finish()
                else Toast.makeText(this, "다시 한 번 더 눌러주세요", Toast.LENGTH_SHORT).show()
            } .addTo(disposable)
    }
}