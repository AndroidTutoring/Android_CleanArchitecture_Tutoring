package com.example.presentation

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.example.data.model.UserDataModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserPresentationDataModel(val name: String,
                                     val id: String,
                                     val date: String,
                                     val url: String?
                ) : Parcelable {


    companion object {

        fun toPresentationModel(
            user: List<UserPresentationDataModel>
        ): List<UserPresentationDataModel> {
            return user.toList().map {
                UserPresentationDataModel(
                    it.name,
                    it.id,
                    it.date,
                    it.url
                )
            }
        }

        fun toDataModel(
            user: List<UserDataModel>
        ): List<UserDataModel> {
            return user.toList().map {
                UserDataModel(
                    it.name,
                    it.id,
                    it.date,
                    it.url
                )
            }
        }
    }
}




