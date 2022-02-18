package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.repository.RepoRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.PresentationUserRepo
import com.example.presentation.model.PresentationUserRepo.Companion.toPresentationModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailViewModel(
    private val repoRepository: RepoRepository
) : BaseViewModel() {


    private val _repoDetailList = MutableLiveData<List<PresentationUserRepo>>()
    val repoDetailList: LiveData<List<PresentationUserRepo>> = _repoDetailList


    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error


    fun getRepoDetails(userName: String) {
        repoRepository.getUserRepoList(userName = userName)
            .subscribeOn(Schedulers.io())
            .filter { !it.isNullOrEmpty() }
            .map { dataModelUserRepoList ->
                dataModelUserRepoList.map { toPresentationModel(it) }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ dataModelUserRepoList ->
                _repoDetailList.value = dataModelUserRepoList
            }, { t ->
                _error.value = Throwable("레포지토리 리스트 받아오는 중 문제 생김")
            })
            .addTo(compositeDisposable)
    }

}