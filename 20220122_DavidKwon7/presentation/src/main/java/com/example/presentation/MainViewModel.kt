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

    private val backButtonSubject : Subject<Boolean> = PublishSubject.create()
    private val publishSubject : PublishSubject<List<User>> =
        PublishSubject.create()
    private val behaviorSubject = BehaviorSubject.createDefault(0L)
    private val localDataSource = LocalDataSourceImpl(dao = userdao)
    private val remoteDataSource = RemoteDataSourceImpl(api)
    private val githubRepository = GithubRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource)
    private val compositeDisposable : CompositeDisposable by lazy {
        CompositeDisposable()
    }
    fun update(githubRepos: List<User>) {
        this.githubRepos.clear()
        this.githubRepos.addAll(githubRepos)
        this.githubRepos.size
        this.githubRepos.take(30)
    }

    private fun initRepo(){
        compositeDisposable.add(githubRepository.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(2)
            .subscribe{ item ->
                update(item)
                publishSubject.onNext(item)
            }.addTo(compositeDisposable)
        )
    }

    //뒤로 가기
    private fun back2() {
        behaviorSubject
            .buffer(2, 1)
            .map { it[1] to it[0]  }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                backButtonSubject.onError(Throwable("다시 한 번 시도해주세요"))
            }
            .subscribe {
                if (it.second - it.first < 2000L){
                    backButtonSubject.onNext(true)
                } else {
                    backButtonSubject.onNext(false)
                }

            } .addTo(compositeDisposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}