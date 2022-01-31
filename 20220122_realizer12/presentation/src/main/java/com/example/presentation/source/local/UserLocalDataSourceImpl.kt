package com.example.presentation.source.local

import com.example.presentation.model.SearchedUser
import com.example.presentation.room.FavoriteDao

class UserLocalDataSourceImpl(private val favoriteDao: FavoriteDao?):UserLocalDataSource {
    override fun addFavoriteUser(vararg favoriteUser: SearchedUser) {
       favoriteDao?.setFavoriteMark(*favoriteUser)
    }

    override fun deleteFavoriteUser(vararg favoriteUser: SearchedUser) {
        //지워지는  즐겨찾기 전체 for 문돌려서 없애줌.
        favoriteUser.forEach { deletedFavoriteUser ->
            favoriteDao?.deleteFavoriteUser(deletedFavoriteUser.id)
        }
    }

    //즐겨찾기 목록 모두 가져옴.
    override fun getFavoriteUsers(): List<SearchedUser>? = favoriteDao?.getFavoriteGitUsers(true)
}