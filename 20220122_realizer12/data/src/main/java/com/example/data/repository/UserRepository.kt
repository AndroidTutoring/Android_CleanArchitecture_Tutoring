package com.example.data.repository

import com.example.data.model.SearchedUser
import com.example.data.model.SearchedUsers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response

interface UserRepository {
    fun deleteFavoriteUser(deletedFavoriteUser: SearchedUser): Completable//유저 즐겨찾기에서 지우기
    fun addFavoriteUser(favoriteUser: SearchedUser): Completable//유저 즐겨찾기에 추가
    fun getFavoriteUsers(): Single<List<SearchedUser>>//즐겨 찾기 유저 다 가져오기
    fun getSearchUsers(
        query: String,
        page: Int,
        perPage: Int
    ): Single<Response<SearchedUsers>>
}