package com.example.presentation.viewmodel

import android.view.View
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.UserRepo
import com.example.presentation.repository.RepoRepository
import com.example.presentation.repository.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class DetailViewModel(
    private val repoRepository: RepoRepository
) : BaseViewModel() {

    val repoDetailPublishSubject: PublishSubject<List<UserRepo>>
        = PublishSubject.create()

    fun getRepoDetails(userName:String){
        repoRepository.getUserRepoList(userName = userName)
            .subscribeOn(Schedulers.io())
            .filter { !it.isNullOrEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userRepoList ->
                repoDetailPublishSubject.onNext(userRepoList)
            }, { t ->
                repoDetailPublishSubject.onError(Throwable("레포지토리 리스트 받아오는 중 문제 생김"))
            }).addTo(compositeDisposable)
    }

}