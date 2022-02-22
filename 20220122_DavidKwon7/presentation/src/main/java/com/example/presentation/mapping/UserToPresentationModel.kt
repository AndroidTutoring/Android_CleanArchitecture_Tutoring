package com.example.presentation.mapping

import com.example.data.model.UserDataModel
import com.example.presentation.UserPresentationDataModel

interface UserToPresentationModel <in U : UserDataModel, out P : UserPresentationDataModel> {
    fun toPresentationModel(user : U) : P

}
