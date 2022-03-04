package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.usecase.GetInitialSearchedUserUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.SearchedUserPresentationModel
import com.example.presentation.model.SearchedUserPresentationModel.Companion.toPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getInitialSearchedUserUseCase: GetInitialSearchedUserUseCase
) : BaseViewModel() {

    //유저리스트 라이브데이터
    private val _searchedUserList = MutableLiveData<List<SearchedUserPresentationModel>>()
    val searchedUsersList: LiveData<List<SearchedUserPresentationModel>> = _searchedUserList

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun searchUsers() {
        getInitialSearchedUserUseCase.getSearchedUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({searchedUsersList->
                _searchedUserList.value = searchedUsersList.map { toPresentationModel(it) }
            },{
                _error.value = Throwable("유저를 검색하는 중에문제가 생감")
            })
            .addTo(compositeDisposable)
    }

}