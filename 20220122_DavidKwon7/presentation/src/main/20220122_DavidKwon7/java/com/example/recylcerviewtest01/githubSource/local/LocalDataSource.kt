package com.example.recylcerviewtest01.githubSource.local

import com.example.recylcerviewtest01.User
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

interface LocalDataSource {
    fun getCachedUserList() : Single<List<User>>
    fun addFav()  : Completable
    fun deleteFav() :Completable

}