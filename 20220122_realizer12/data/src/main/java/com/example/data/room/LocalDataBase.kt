package com.example.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.SearchedUser

@Database(
    entities = [SearchedUser::class],
    version = 3,
    exportSchema = false//schema 구조 export false처리
)
abstract class LocalDataBase : RoomDatabase() {
    abstract fun getFavoriteMarkDao(): FavoriteDao

    companion object {
        fun getInstance(context: Context): LocalDataBase {
            synchronized(LocalDataBase::class) {//중복생성  방지
                return Room.databaseBuilder(
                    context.applicationContext,
                    LocalDataBase::class.java, "local-database.db"
                ).fallbackToDestructiveMigration()//버전 업데이트시 기존 데이터 못찾으면, illegalStateException  방지
                    .allowMainThreadQueries()
                    .build()
            }
        }
    }
}