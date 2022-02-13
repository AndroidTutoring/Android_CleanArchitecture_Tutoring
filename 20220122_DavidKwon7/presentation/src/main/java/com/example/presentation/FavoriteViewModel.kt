package com.example.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.model.UserDataModel
import com.example.data.repository.githubRepository.GithubRepository
import com.example.data.repository.githubRepository.GithubRepositoryImpl
import com.example.data.repository.githubSource.local.LocalDataSourceImpl
import com.example.data.repository.githubSource.remote.RemoteDataSourceImpl
import com.example.presentation.databinding.ActivityFavoriteBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class FavoriteViewModel : ViewModel() {

    private lateinit var api: Api
    private lateinit var userdao: UserDao
    private val githubRepos: ArrayList<UserDataModel> = ArrayList()
    private lateinit var compositeDisposable: CompositeDisposable

    private val localDataSource = LocalDataSourceImpl(dao = userdao)
    private val remoteDataSource = RemoteDataSourceImpl(api)
    private val githubRepository = GithubRepositoryImpl(
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource)
    val publishSubject : PublishSubject<List<UserDataModel>> =
        PublishSubject.create()

    lateinit var adapter: FavoriteAdapter
    lateinit var binding: ActivityFavoriteBinding




    @SuppressLint("CheckResult")
    private fun setAdapter(){
        binding.rvProfile.adapter = this.adapter
    }


    fun update(githubRepos: List<UserDataModel>) {
        this.githubRepos.clear()
        this.githubRepos.addAll(githubRepos)
        this.githubRepos.size
        this.githubRepos.take(30)
    }

    private fun initRepo(){
        githubRepository.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .retry(2)
            .subscribe{ item ->
                update(item)
                publishSubject.onNext(item)
            }.addTo(compositeDisposable)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}