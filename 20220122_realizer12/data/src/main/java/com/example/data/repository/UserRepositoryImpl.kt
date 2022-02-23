package com.example.data.repository

import com.example.data.model.SearchedUser
import com.example.data.source.local.UserLocalDataSource
import com.example.data.source.remote.UserRemoteDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun deleteFavoriteUser(deletedFavoriteUser: SearchedUser): Completable =
        userLocalDataSource.deleteFavoriteUser(deletedFavoriteUser)


    override fun addFavoriteUser(favoriteUser: SearchedUser): Completable =
        userLocalDataSource.addFavoriteUser(favoriteUser)


    override fun getFavoriteUsers(): Single<List<SearchedUser>> =
        userLocalDataSource.getFavoriteUsers()

    override fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int
    ) = userRemoteDataSource.getSearchUsers(
        query, page, perPage
    )


}