package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetSearchedUsersListUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.SearchedUserPresentationModel
import com.example.presentation.model.SearchedUsersPresentationModel.Companion.toPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    //유저리스트 라이브데이터
    private val _searchedUserList = MutableLiveData<List<SearchedUserPresentationModel>>()
    val searchedUsersList: LiveData<List<SearchedUserPresentationModel>> = _searchedUserList

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun searchUsers() {
        Single.zip(
            userRepository.getSearchUsers()
                .subscribeOn(Schedulers.io())
                .retryWhen { errorObservable ->
                    errorObservable
                        .delay(3, TimeUnit.SECONDS)
                        .take(2)
                }.observeOn(AndroidSchedulers.mainThread()),
            Single.timer(2, TimeUnit.SECONDS),
            { dataModelSearchedUsers, _ ->
                return@zip dataModelSearchedUsers
            })
            .map { searchedUsersList ->
                searchedUsersList?.let {
                    toPresentationModel(
                        it
                    ).items
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchedUsersList ->
                _searchedUserList.value = searchedUsersList
            }, {
                _error.value = Throwable("유저를 검색하는 중에문제가 생감")
            })
            .addTo(compositeDisposable)
    }

}