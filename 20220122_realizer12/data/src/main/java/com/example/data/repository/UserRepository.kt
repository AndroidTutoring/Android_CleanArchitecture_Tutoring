package com.example.data.repository

import com.example.data.model.SearchedUserDataModel
import com.example.data.model.SearchedUsersDataModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun deleteFavoriteUser(deletedFavoriteUser: SearchedUserDataModel): Completable//유저 즐겨찾기에서 지우기
    fun addFavoriteUser(favoriteUser: SearchedUserDataModel): Completable//유저 즐겨찾기에 추가
    fun getFavoriteUsers(): Single<List<SearchedUserDataModel>>//즐겨 찾기 유저 다 가져오기
    fun getSearchUsers(
        query: String = "realizer12",
        page: Int = 1,
        perPage: Int = 10
    ): Single<SearchedUsersDataModel>
}