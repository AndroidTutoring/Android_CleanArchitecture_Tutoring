package com.example.presentation.source.local

import com.example.presentation.model.SearchedUser

//로컬 용 데이터소스
interface UserLocalDataSource {
    fun deleteFavoriteUser(vararg favoriteUser: SearchedUser)//유저 즐겨찾기에서 지우기
    fun addFavoriteUser(vararg favoriteUser:SearchedUser)//유저 즐겨찾기에 추가
    fun getFavoriteUsers():List<SearchedUser>?//즐겨 찾기 유저 다 가져오기
}