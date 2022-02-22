package com.example.local.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class RoomDatabase : RoomDatabase() {
    abstract fun userDao() : com.example.local.room.UserDao

    /*companion object{
        @Volatile
        private var INSTANCE : RoomDatabase?=null

        fun getDatabase(context: Context) : RoomDatabase? {
            if (INSTANCE ==null){
                synchronized(RoomDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RoomDatabase::class.java,
                        "RoomDatabase").build()
                }
            }
            return INSTANCE
        }
    }*/
}