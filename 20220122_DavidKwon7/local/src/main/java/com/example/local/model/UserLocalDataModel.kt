package com.example.local.model

import com.example.data.model.UserDataModel
import com.example.local.mapping.LocalToUserMapper

data class UserLocalDataModel(
    val name: String,
    val id: String,
    val date: String,
    val url: String?
) {
    companion object : LocalToUserMapper<UserLocalDataModel, UserDataModel> {
        override fun toUser(UserLocalDataModel: UserLocalDataModel): UserDataModel {
            UserDataModel(
                name = "",
                id = "",
                url = "",
                date = ""
            )
            //response 처리???  .. 이게 맞는 건가?
            return UserDataModel(
                name = "",
                id = "",
                url = "",
                date = ""
            )
        }

    }
}
