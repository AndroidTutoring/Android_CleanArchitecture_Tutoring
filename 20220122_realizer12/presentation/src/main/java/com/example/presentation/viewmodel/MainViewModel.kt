package com.example.presentation.viewmodel

import com.example.presentation.base.BaseViewModel
import com.example.presentation.model.SearchedUser
import com.example.presentation.repository.UserRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject

class MainViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    //현재  즐겨찾기 유저 목록  publishSubject
    val currentFavoriteUserListPublishSubject: PublishSubject<List<SearchedUser>> =
        PublishSubject.create()

    //즐겨찾기 지우기
    fun deleteFavoriteUsers(
        searchedUser: SearchedUser,
        currentList: MutableList<SearchedUser>
    ) {
        userRepository.deleteFavoriteUser(searchedUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                throw it
            }
            .doOnComplete {

                val newList = currentList.toMutableList()
                newList.removeAll { it.id == searchedUser.id }

                currentFavoriteUserListPublishSubject.onNext(newList)

            }.subscribe()
            .addTo(compositeDisposable)
    }


    fun getFavoriteUsers() {
        userRepository.getFavoriteUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ favoriteUsers ->
                currentFavoriteUserListPublishSubject.onNext(favoriteUsers)
            }, {
                throw it
            })
            .addTo(compositeDisposable)
    }


    override fun onCleared() {
        super.onCleared()
    }
}