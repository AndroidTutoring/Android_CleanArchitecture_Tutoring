package com.example.presentation

import android.os.Parcelable
import com.example.data.model.UserDataModel
import com.example.presentation.mapping.UserToPresentationModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPresentationDataModel(
    val name: String,
    val id: String,
    val date: String,
    val url: String?
) : Parcelable {
    companion object :
        UserToPresentationModel<UserDataModel, UserPresentationDataModel> {
        override fun toPresentationModel(
            user: UserDataModel
        ): UserPresentationDataModel =
            with(user) {
                UserPresentationDataModel(
                    name = name,
                    id = id,
                    date = date,
                    url = url
                )
            }
    }

}






