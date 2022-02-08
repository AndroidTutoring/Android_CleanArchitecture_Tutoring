package com.example.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private val adapter: ProfileAdapter by lazy {
        ProfileAdapter(listData,this)
    }
    private val backButtonSubject : Subject<Long> = BehaviorSubject.createDefault(0L)
    val localDataSource = LocalDataSourceImpl(dao = userdao)
    val remoteDataSource = RemoteDataSourceImpl(api)
    val githubRepository = GithubRepositoryImpl(localDataSource = localDataSource,
    remoteDataSource = remoteDataSource)

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickFavorite()
        back2()
        itemFavClick()


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


    fun update(githubRepos: List<User>) {
        this.githubRepos.clear()
        this.githubRepos.addAll(githubRepos)
        this.githubRepos.size
        this.githubRepos.take(30)
    }

    private fun AdapterData(){
        with(adapter){
            intent.putExtra("name", position?.let { postList.get(it).name })
            intent.putExtra("id", position?.let { postList.get(it).id })
            intent.putExtra("date", position?.let { postList.get(it).date })
            intent.putExtra("url", position?.let { postList.get(it).url })
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
    //뒤로 가기
    private fun back2() {
        backButtonSubject
            .buffer(2, 1)
            .map { it[1] - it[0] < 1500 }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { willFinish ->
                if (willFinish) finish()
                else Toast.makeText(this, "다시 한 번 더 눌러주세요", Toast.LENGTH_SHORT).show()
            } .addTo(disposables)
    }


}