package com.example.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.data.repository.githubRepository.GithubRepositoryImpl
import com.example.data.repository.githubSource.local.LocalDataSourceImpl
import com.example.data.repository.githubSource.remote.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class MainViewModel : ViewModel() {
    private lateinit var api: Api
    private lateinit var userdao: UserDao
    private val listData = listOf<User>()
    private val githubRepos: ArrayList<User> = ArrayList()
    private val backButtonSubject : Subject<Long> = BehaviorSubject.createDefault(0L)


    val publishSubject : PublishSubject<List<User>> =
        PublishSubject.create()
    val localDataSource = LocalDataSourceImpl(dao = userdao)
    val remoteDataSource = RemoteDataSourceImpl(api)
    val githubRepository = GithubRepositoryImpl(
        localDataSource = localDataSource,
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

    private fun initRepo(){
        githubRepository
        disposables.add(githubRepository.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(2)
            .subscribe{ item ->
                update(item)
                publishSubject.onNext(item)
            }
        )
    }

    //뒤로 가기
    private fun back2() {
        backButtonSubject
            .buffer(2, 1)
            .map { it[1] - it[0] < 1500 }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                publishSubject.onError(Throwable("다시 한 번 시도해주세요"))
            }
            .subscribe { item ->
                if (item) {
                    publishSubject.onNext(listData)
                }
                else
                    backButtonSubject.retry(3)
            } .addTo(disposables)
    }


    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}