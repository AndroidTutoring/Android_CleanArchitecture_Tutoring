package com.example.presentation.viewmodel

import com.example.presentation.base.BaseViewModel
import com.example.data.model.SearchedUser
import com.example.data.repository.UserRepository
import com.example.presentation.model.PresentationSearchedUser
import com.example.presentation.model.PresentationSearchedUsers
import com.example.presentation.model.PresentationSearchedUsers.Companion.toPresentationModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class SplashViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    //유저 프래그먼트 유저리스트 업데이트 용
    val searchedUserPublishSubject: PublishSubject<List<PresentationSearchedUser>>
        = PublishSubject.create()

    fun searchUsers(){
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
                searchedUserPublishSubject.onNext(dataModelSearchedUsers?.let {
                    toPresentationModel(
                        it
                    ).items
                })
            }).onErrorReturn {
            searchedUserPublishSubject.onError(Throwable("유저 정보를 가져올수가 없습니다."))
        }.subscribe()
        .addTo(compositeDisposable)
    }

}