package com.example.presentation.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.presentation.model.SearchedUser

//즐겨 찾기 관련  쿼리 모음
@Dao
interface FavoriteDao {
    @Insert(onConflict = REPLACE)
    fun setFavoriteMark(vararg searchedUser: SearchedUser)//즐겨 찾기 클릭시 local db에 추가

    //내가 즐겨찾기한  깃 유저들 모두 가져오기
    @Query("SELECT * FROM favoriteMarkTable WHERE isMyFavorite = :isMyFavorite")
    fun getFavoriteGitUsers(isMyFavorite:Boolean): List<SearchedUser>

}