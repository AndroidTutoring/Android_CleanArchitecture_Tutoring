package com.example.presentation.repository

import com.example.presentation.model.SearchedUser
import com.example.presentation.model.SearchedUsers
import com.example.presentation.source.local.UserLocalDataSource
import com.example.presentation.source.local.UserLocalDataSourceImpl
import com.example.presentation.source.remote.UserRemoteDataSource
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
):UserRepository {

    override fun deleteFavoriteUser(vararg favoriteUser: SearchedUser) {
        userLocalDataSource.deleteFavoriteUser(*favoriteUser)
    }

    override fun addFavoriteUser(vararg favoriteUser: SearchedUser) {
        userLocalDataSource.addFavoriteUser(*favoriteUser)
    }

    override fun getFavoriteUsers(): List<SearchedUser>? = userLocalDataSource.getFavoriteUsers()
    override fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int
    ) = userRemoteDataSource.getSearchUsers(
            query, page, perPage
        )



}