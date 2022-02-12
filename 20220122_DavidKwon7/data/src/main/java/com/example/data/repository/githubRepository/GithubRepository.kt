package com.example.data.repository.githubRepository

import com.example.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface GithubRepository {

    fun getCachedUserList() : Single<List<User>>
    fun addFav(favoriteUser: User)  : Completable
    fun deleteFav(deleteUser: com.example.presentation.User) : Completable

    fun getRepos() : Single<List<User>>

}