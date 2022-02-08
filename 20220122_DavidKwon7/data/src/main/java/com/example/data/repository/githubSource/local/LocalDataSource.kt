package com.example.data.repository.githubSource.local

import com.example.presentation.User
import io.reactivex.Completable
import io.reactivex.Single

interface LocalDataSource {
    fun getCachedUserList() : Single<List<User>>
    fun addFav(favoriteUser : User)  : Completable
    fun deleteFav(deleteUser : User) : Completable
}