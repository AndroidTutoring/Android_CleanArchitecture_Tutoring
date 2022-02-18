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

    //레포리스트 라이브데이터
    private val _repoDetailList = MutableLiveData<List<PresentationUserRepo>>()
    val repoDetailList: LiveData<List<PresentationUserRepo>> = _repoDetailList

    //error 관련 처리용  라이브 데이터
    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error


    fun getRepoDetails(userName: String) {
        repoRepository.getUserRepoList(userName = userName)
            .subscribeOn(Schedulers.io())
            .filter { !it.isNullOrEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ dataModelUserRepoList ->
                val presentationUserRepoList = dataModelUserRepoList.map { toPresentationModel(it) }
                _repoDetailList.value = presentationUserRepoList
            }, { t ->
                _error.value = Throwable("레포지토리 리스트 받아오는 중 문제 생김")
            })
            .addTo(compositeDisposable)
    }

}