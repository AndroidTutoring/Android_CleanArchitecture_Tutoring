package com.example.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.local.room.RoomDatabase
import com.example.data.model.UserDataModel
import com.example.data.repository.githubRepository.GithubRepository
import com.example.data.repository.githubRepository.GithubRepositoryImpl
import com.example.data.repository.githubSource.local.LocalDataSource
import com.example.data.repository.githubSource.remote.RemoteDataSource
import com.example.local.room.UserDao
import com.example.local.source.LocalDataSourceImpl
import com.example.remote.retrofit.GithubAPI
import com.example.remote.source.RemoteDataSourceImpl
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