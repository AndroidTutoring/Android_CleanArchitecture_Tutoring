package com.example.local.room

import androidx.room.RoomDatabase

abstract class RoomDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao


}