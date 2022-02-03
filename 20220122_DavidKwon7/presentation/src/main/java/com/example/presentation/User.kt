package com.example.presentation

import androidx.room.Entity

@Entity
data class User(val name: String?,
                val id: String?,
                val date: String?,
                val url: String?)
