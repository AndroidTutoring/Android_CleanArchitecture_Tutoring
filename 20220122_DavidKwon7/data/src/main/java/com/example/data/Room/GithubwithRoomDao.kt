package com.example.presentation

import androidx.room.*
import com.example.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addFav(user: User) : Completable

    @Query("SELECT * FROM user")
    fun loadUserList(): Single<List<User>>

    @Delete
    fun deleteFav(user: User) : Completable
}