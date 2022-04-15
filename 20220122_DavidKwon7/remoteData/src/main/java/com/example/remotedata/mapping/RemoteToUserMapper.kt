package com.example.remotedata.mapping

import com.example.data.model.UserDataModel
import com.example.remotedata.model.UserRemoteDataModel

interface RemoteToUserMapper <in R : UserRemoteDataModel, out U : UserDataModel> {
    fun toUser(userRemoteDataModel : R) : U
}