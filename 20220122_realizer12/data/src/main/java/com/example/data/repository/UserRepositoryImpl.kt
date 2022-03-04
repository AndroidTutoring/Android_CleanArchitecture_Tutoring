package com.example.data.repository

import com.example.data.model.SearchedUserDataModel
import com.example.data.model.SearchedUsersDataModel
import com.example.data.source.local.UserLocalDataSource
import com.example.data.source.remote.UserRemoteDataSource
import com.example.domain.entity.SearchedUserEntity
import com.example.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override fun deleteFavoriteUser(deletedFavoriteUser: SearchedUserEntity): Completable =
        userLocalDataSource.deleteFavoriteUser(SearchedUserDataModel.toDataModel(deletedFavoriteUser))


    override fun addFavoriteUser(favoriteUser: SearchedUserEntity): Completable =
        userLocalDataSource.addFavoriteUser(SearchedUserDataModel.toDataModel(favoriteUser))


    override fun getFavoriteUsers(): Single<List<SearchedUserEntity>> =
        userLocalDataSource.getFavoriteUsers().map { it.map { SearchedUserDataModel.toEntity(it)} }

    override fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int
    ) = userRemoteDataSource.getSearchUsers(
        query, page, perPage
    ).map { SearchedUsersDataModel.toEntity(it)}


}