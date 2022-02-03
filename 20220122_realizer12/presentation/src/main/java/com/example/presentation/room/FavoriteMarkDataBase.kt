package com.example.presentation.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.presentation.model.SearchedUser

@Database(
    entities = [SearchedUser::class],
    version = 3,
    exportSchema = false//schema 구조 export false처리
)
abstract class FavoriteMarkDataBase : RoomDatabase() {
    abstract fun getFavoriteMarkDao(): FavoriteDao

    companion object {
        private var INSTANCE: FavoriteMarkDataBase? = null
        fun getInstance(context: Context): FavoriteMarkDataBase? {
            if (INSTANCE == null) {
                synchronized(FavoriteMarkDataBase::class) {//중복생성  방지
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteMarkDataBase::class.java, "local-database.db"
                    )
                        .fallbackToDestructiveMigration()//버전 업데이트시 기존 데이터 못찾으면, illegalStateException  방지
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}