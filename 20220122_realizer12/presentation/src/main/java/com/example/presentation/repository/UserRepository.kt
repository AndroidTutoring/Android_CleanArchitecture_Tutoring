package com.example.presentation.repository

import com.example.presentation.model.SearchedUser
import com.example.presentation.model.SearchedUsers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface UserRepository {
    fun deleteFavoriteUser(deletedFavoriteUser: SearchedUser):Completable//유저 즐겨찾기에서 지우기
    fun addFavoriteUser(vararg favoriteUser: SearchedUser):Completable//유저 즐겨찾기에 추가
    fun getFavoriteUsers():Single<List<SearchedUser>>//즐겨 찾기 유저 다 가져오기
    fun getSearchUsers(
        query:String,
        page:Int,
        perPage:Int
    ): Single<Response<SearchedUsers>>
}