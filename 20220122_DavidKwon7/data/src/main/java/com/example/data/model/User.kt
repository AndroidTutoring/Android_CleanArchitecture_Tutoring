package com.example.data.model


data class User(val name: String,
                val id: String,
                val date: String,
                val url: String?)

fun mapper(user:List<User>) : List<User>{
    return user.toList().map {
        User(
            it.name,
            it.id,
            it.date,
            it.url
        )
    }
}