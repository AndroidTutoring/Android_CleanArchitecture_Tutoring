package com.example.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.example.data.repository.githubRepository.GithubRepositoryImpl
import com.example.data.repository.githubSource.local.LocalDataSourceImpl
import com.example.data.repository.githubSource.remote.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MainViewModel : ViewModel() {
    private lateinit var api: Api
    private lateinit var userdao: UserDao
    private val listData = listOf<User>()
    private val githubRepos: ArrayList<User> = ArrayList()

    val subject : PublishSubject<List<User>> =
        PublishSubject.create()
    val localDataSource = LocalDataSourceImpl(dao = userdao)
    val remoteDataSource = RemoteDataSourceImpl(api)
    val githubRepository = GithubRepositoryImpl(localDataSource = localDataSource,
        remoteDataSource = remoteDataSource)
    private val disposables : CompositeDisposable by lazy {
        CompositeDisposable()
    }
    fun update(githubRepos: List<User>) {
        this.githubRepos.clear()
        this.githubRepos.addAll(githubRepos)
        this.githubRepos.size
        this.githubRepos.take(30)
    }

    @SuppressLint("CheckResult")
    private fun initRepo(){
        githubRepository
        disposables.add(githubRepository.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(2)
            .subscribe{ item ->
                update(item)
                subject.onNext(item)
            }
        )
    }

}