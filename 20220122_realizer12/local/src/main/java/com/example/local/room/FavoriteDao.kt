package com.example.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.data.model.SearchedUserDataModel
import com.example.local.model.SearchedUserLocalModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

//즐겨 찾기 관련  쿼리 모음
@Dao
interface FavoriteDao {
    @Insert(onConflict = REPLACE)
    fun setFavoriteMark(vararg searchedUser: SearchedUserLocalModel): Completable//즐겨 찾기 클릭시 local db에 추가

    //내가 즐겨찾기한  깃 유저들 모두 가져오기
    @Query("SELECT * FROM favoriteMarkTable WHERE is_my_favorite = :isMyFavorite")
    fun getFavoriteGitUsers(isMyFavorite: Boolean): Single<List<SearchedUserLocalModel>>

    @Delete
    fun deleteFavoriteUser(deletedFavoriteUser: SearchedUserLocalModel): Completable
}