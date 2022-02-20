package com.example.data.repository.githubSource.local

import com.example.data.model.UserDataModel
import io.reactivex.Completable
import io.reactivex.Single

interface LocalDataSource {
    fun getCachedUserList() : Single<List<UserDataModel>>
    fun addFav(favoriteUser : UserDataModel)  : Completable
    fun deleteFav(deleteUser : UserDataModel) : Completable
}