package com.example.presentation

import androidx.room.Entity
import com.example.data.model.User

data class User(val name: String,
                val id: String,
                val date: String,
                val url: String?)

fun mapper(user:List<User>) : List<com.example.data.model.User>{
    return user.toList().map {
        User(
            it.name,
            it.id,
            it.date,
            it.url
        )
    }
}