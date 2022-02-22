package com.example.remote.mapping

import com.example.data.model.UserDataModel
import com.example.remote.model.UserRemoteDataModel

interface RemoteToUserMapper<in R : UserRemoteDataModel, out U : UserDataModel> {
    fun toUser(userRemoteDataModel : R) : U
}