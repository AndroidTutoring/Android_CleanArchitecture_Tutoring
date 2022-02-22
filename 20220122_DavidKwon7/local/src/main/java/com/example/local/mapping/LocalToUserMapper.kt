package com.example.local.mapping

import com.example.data.model.UserDataModel
import com.example.local.model.UserLocalDataModel

interface LocalToUserMapper <in R : UserLocalDataModel, out U : UserDataModel> {
    fun toUser(UserLocalDataModel : R) : U
}