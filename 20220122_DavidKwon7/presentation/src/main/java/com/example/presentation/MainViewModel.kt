package com.example.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.localdata.room.RoomDatabase
import com.example.data.model.UserDataModel
import com.example.data.repository.githubRepository.GithubRepository
import com.example.data.repository.githubRepository.GithubRepositoryImpl
import com.example.localdata.source.LocalDataSourceImpl
import com.example.localdata.room.UserDao
import com.example.remotedata.retrofit.GithubAPI
import com.example.remotedata.source.RemoteDataSourceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubRepository: GithubRepository
    ) : ViewModel() {

    var progressBarVisible: MutableLiveData<Boolean> = MutableLiveData()
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
            ?.subscribe({ result->
                    _list.value = result
            })?.addTo(compositeDisposable)
    }

    init {
        githubRepository.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(2)
            .subscribe({result->
                _list.value = result
            }
        ).addTo(compositeDisposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}