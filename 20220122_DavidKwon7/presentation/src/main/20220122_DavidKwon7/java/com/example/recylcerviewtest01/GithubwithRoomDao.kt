package com.example.recylcerviewtest01

import androidx.room.*
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