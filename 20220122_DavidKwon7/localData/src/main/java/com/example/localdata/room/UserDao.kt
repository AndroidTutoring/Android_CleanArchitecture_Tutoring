package com.example.localdata.room

import androidx.room.*
import com.example.data.model.UserDataModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFav(user: UserDataModel) : Completable

    @Query("SELECT * FROM userdatamodel")
    fun loadUserList(): Single<List<UserDataModel>>

    @Delete
    fun deleteFav(user: UserDataModel) : Completable
}