package com.example.local.impl

import android.util.Log
import com.example.data.model.SearchedUserDataModel
import com.example.local.room.FavoriteDao
import com.example.data.source.local.UserLocalDataSource
import com.example.local.model.SearchedUserLocalModel.Companion.toDataModel
import com.example.local.model.SearchedUserLocalModel.Companion.toLocalModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserLocalDataSourceImpl @Inject constructor(
    private val favoriteDao: FavoriteDao
) : UserLocalDataSource {

    override fun addFavoriteUser(favoriteUser: SearchedUserDataModel): Completable {
        return favoriteDao.setFavoriteMark(toLocalModel(favoriteUser))
    }

    override fun deleteFavoriteUser(deletedFavoriteUser: SearchedUserDataModel): Completable {
        Log.v("이거냥ㅇ","ㅁㄴㅇㅁㄴㅇㅁㄴㅇ-> "+deletedFavoriteUser)
        return favoriteDao.deleteFavoriteUser(toLocalModel(deletedFavoriteUser))
    }

    //즐겨찾기 목록 모두 가져옴.
    override fun getFavoriteUsers(): Single<List<SearchedUserDataModel>> =
        favoriteDao.getFavoriteGitUsers(true).map {
            it.map { searchedUserLocal ->  toDataModel(searchedUserLocal) }
        }
}