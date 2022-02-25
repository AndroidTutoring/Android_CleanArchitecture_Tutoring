package com.example.localdata.room

import androidx.room.*
import com.example.data.model.UserDataModel
import com.example.localdata.mapping.LocalToUserMapper
import com.example.localdata.model.UserLocalDataModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFav(
        localUser: Pair<LocalToUserMapper<UserLocalDataModel, UserDataModel>, UserDataModel
                >) : Completable

    @Query("SELECT * FROM user")
    fun loadUserList(): Single<List<UserDataModel>>


    @Delete
    fun deleteFav(
        user: Pair<LocalToUserMapper<UserLocalDataModel, UserDataModel>, UserDataModel
                >) : Completable
}