package com.example.domain.repository


import com.example.domain.entity.SearchedUserEntity
import com.example.domain.entity.SearchedUsersEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface UserRepository {
    fun deleteFavoriteUser(deletedFavoriteUser: SearchedUserEntity): Completable//유저 즐겨찾기에서 지우기
    fun addFavoriteUser(favoriteUser: SearchedUserEntity): Completable//유저 즐겨찾기에 추가
    fun getFavoriteUsers(): Single<List<SearchedUserEntity>>//즐겨 찾기 유저 다 가져오기
    fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): Single<SearchedUsersEntity>
}