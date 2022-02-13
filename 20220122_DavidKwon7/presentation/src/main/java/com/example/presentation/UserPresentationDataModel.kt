package com.example.presentation

import com.example.data.model.UserDataModel

data class UserPresentationDataModel(val name: String,
                val id: String,
                val date: String,
                val url: String?)

fun toPresentationModel(user:List<UserDataModel>) : List<com.example.data.model.UserDataModel>{
    return user.toList().map {
        UserDataModel(
            it.name,
            it.id,
            it.date,
            it.url
        )
    }
}