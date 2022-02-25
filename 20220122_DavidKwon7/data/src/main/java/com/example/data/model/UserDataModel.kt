package com.example.data.model

import androidx.room.Entity

@Entity(tableName = "user")
data class UserDataModel(
    var name: String?,
    var id: String?,
    var date: String?,
    var url: String?
    )

