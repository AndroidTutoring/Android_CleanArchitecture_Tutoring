package com.example.recylcerviewtest01.githubSource.local

import com.example.presentation.User
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface LocalDataSource {
    fun getCachedUserList() : Single<List<User>>
    fun addFav(favoriteUser : User)  : Completable
    fun deleteFav(deleteUser : User) :Completable

}