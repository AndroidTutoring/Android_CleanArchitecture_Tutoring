package com.example.data.source.local

import com.example.data.model.SearchedUser
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

//로컬 용 데이터소스
interface UserLocalDataSource {
    fun deleteFavoriteUser(deletedFavoriteUser: SearchedUser): Completable//유저 즐겨찾기에서 지우기
    fun addFavoriteUser(favoriteUser: SearchedUser): Completable//유저 즐겨찾기에 추가
    fun getFavoriteUsers(): Single<List<SearchedUser>>//즐겨 찾기 유저 다 가져오기
}