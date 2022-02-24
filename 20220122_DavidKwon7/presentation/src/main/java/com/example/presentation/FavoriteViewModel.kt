package com.example.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.model.UserDataModel
import com.example.data.repository.githubRepository.GithubRepository
import com.example.data.repository.githubRepository.GithubRepositoryImpl
import com.example.localdata.source.LocalDataSourceImpl
import com.example.localdata.room.UserDao
import com.example.remotedata.source.RemoteDataSourceImpl
import com.example.presentation.databinding.ActivityFavoriteBinding
import com.example.remotedata.retrofit.GithubAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private lateinit var compositeDisposable: CompositeDisposable

    lateinit var adapter: FavoriteAdapter
    lateinit var binding: ActivityFavoriteBinding

    private val _list = MutableLiveData<List<UserDataModel>>()
    val list : LiveData<List<UserDataModel>> = _list


    @SuppressLint("CheckResult")
    private fun setAdapter(){
        binding.rvProfile.adapter = this.adapter
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