package com.example.remotedata.model

import com.example.data.model.UserDataModel
import com.example.remotedata.mapping.RemoteToUserMapper

data class UserRemoteDataModel(
    val name: String,
    val id: String,
    val date: String,
    val url: String?
) {
    companion object : RemoteToUserMapper<UserRemoteDataModel, UserDataModel> {
        override fun toUser(userRemoteDataModel: UserRemoteDataModel): UserDataModel =
            UserDataModel (
                name = "",
                id = "",
                url = "",
                date = ""
            )

    }
}
