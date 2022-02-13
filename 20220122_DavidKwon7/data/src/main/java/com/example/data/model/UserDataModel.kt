package com.example.data.model

import androidx.room.Entity

@Entity

data class UserDataModel(val name: String,
                         val id: String,
                         val date: String,
                         val url: String?)

