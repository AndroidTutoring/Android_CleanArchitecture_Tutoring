package com.example.localdata.mapping

import com.example.data.model.UserDataModel
import com.example.localdata.model.UserLocalDataModel

interface LocalToUserMapper <in R : UserLocalDataModel, out U : UserDataModel> {
    fun toUser(UserLocalDataModel : R) : U
}