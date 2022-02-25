package com.example.localdata.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.localdata.model.UserLocalDataModel

@Database(entities = [UserLocalDataModel::class], version = 1)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao

}