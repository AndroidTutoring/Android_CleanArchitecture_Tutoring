package com.example.data.Room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.presentation.UserDao

abstract class RoomDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

    companion object{
        @Volatile
        private var INSTANCE : RoomDatabase?=null

        fun getDatabase(context: Context) : RoomDatabase? {
            if (INSTANCE==null){
                synchronized(RoomDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RoomDatabase::class.java,
                        "RoomDatabase").build()
                }
            }
            return INSTANCE
        }
    }
}