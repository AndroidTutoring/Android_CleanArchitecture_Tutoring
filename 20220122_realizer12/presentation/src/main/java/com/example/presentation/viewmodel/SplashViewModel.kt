package com.example.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.repository.UserRepository
import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.model.PresentationSearchedUsers.Companion.toPresentationModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    //유저리스트 라이브데이터
    private var _searchedUserList = MutableLiveData<List<PresentationSearchedUser>>()
    val searchedUsersList: LiveData<List<PresentationSearchedUser>> = _searchedUserList

    private var _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable> = _error

    fun searchUsers() {
        Single.zip(
            userRepository.getSearchUsers(query = "realizer12", 1, 10)
                .subscribeOn(Schedulers.io()).map {
                    if (it.isSuccessful) {
                        it.body()
                    } else {
                        throw Throwable()
                    }
                }.retryWhen { errorObservable ->
                    errorObservable
                        .delay(3, TimeUnit.SECONDS)
                        .take(2)
                }.observeOn(AndroidSchedulers.mainThread()),
            Single.timer(2, TimeUnit.SECONDS),
            { dataModelSearchedUsers, _ ->
                return@zip dataModelSearchedUsers
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ searchedUsersList ->
                _searchedUserList.value = searchedUsersList?.let {
                    toPresentationModel(
                        it
                    ).items
                }
            }, {
                _error.value = Throwable("유저를 검색하는 중에문제가 생감")
            })
            .addTo(compositeDisposable)
    }

}