package com.example.presentation

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.example.data.model.UserDataModel
import com.example.presentation.mapping.PresentationToUserMapper
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserPresentationDataModel(
    val name: String,
    val id: String,
    val date: String,
    val url: String?
) : Parcelable {


    companion object :
        PresentationToUserMapper<UserDataModel, UserPresentationDataModel> {

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






