package com.example.presentation.mapping

import com.example.data.model.UserDataModel
import com.example.presentation.UserPresentationDataModel

interface PresentationToUserMapper <in U : UserDataModel, out P : UserPresentationDataModel> {
    fun toPresentationModel(user : U) : P
}