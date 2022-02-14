package com.example.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.Room.RoomDatabase
import com.example.data.model.UserDataModel
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

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var api= GithubAPI::class.java
    private lateinit var userdao: UserDao
    private val listData = listOf<UserDataModel>()
    private val githubRepos: ArrayList<UserDataModel> = ArrayList()
    var progressBarVisible: MutableLiveData<Boolean> = MutableLiveData()
    private val backButtonSubject : Subject<Long> =
        BehaviorSubject.createDefault(0L)
    val publishSubject : PublishSubject<List<UserDataModel>> =
        PublishSubject.create()
    private val behaviorSubject = BehaviorSubject.createDefault(0L)
    private val localDataSource = LocalDataSourceImpl(dao = userdao)
    private val remoteDataSource = RemoteDataSourceImpl()
    private val githubRepository = GithubRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource)
    private val compositeDisposable : CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val _list = MutableLiveData<List<UserDataModel>>()
    val list : LiveData<List<UserDataModel>> = _list

    private var roomDatabaseInstance : RoomDatabase?=null
    fun setInstanceDB(roomDatabaseInstance : RoomDatabase){
        this.roomDatabaseInstance = roomDatabaseInstance
    }

    fun saveData(data:UserDataModel){
        roomDatabaseInstance?.userDao()?.loadUserList()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ it->
                    _list.value = it
            })?.addTo(compositeDisposable)
    }

    init {
        compositeDisposable.add(githubRepository.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(2)
            .subscribe({it->
                _list.value = it
            }
            )
        )
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}