package com.example.data.repository.githubRepository

import com.example.data.model.UserDataModel
import io.reactivex.Completable
import io.reactivex.Single

interface GithubRepository {

    fun getCachedUserList() : Single<List<UserDataModel>>
    fun addFav(favoriteUser: UserDataModel)  : Completable
    fun deleteFav(deleteUser: UserDataModel) : Completable

    fun getRepos() : Single<List<UserDataModel>>

}