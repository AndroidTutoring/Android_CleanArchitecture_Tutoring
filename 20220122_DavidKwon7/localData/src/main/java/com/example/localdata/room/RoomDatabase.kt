package com.example.localdata.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class RoomDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

}