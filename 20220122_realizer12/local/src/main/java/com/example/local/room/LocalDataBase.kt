package com.example.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.local.model.SearchedUserLocal

@Database(
    entities = [SearchedUserLocal::class],
    version = 3,
    exportSchema = false//schema 구조 export false처리
)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun getFavoriteMarkDao(): FavoriteDao
}