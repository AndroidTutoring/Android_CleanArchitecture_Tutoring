package com.example.localdata.model

import androidx.room.Entity
import com.example.data.model.UserDataModel
import com.example.localdata.mapping.LocalToUserMapper

@Entity(tableName = "localUser")
data class UserLocalDataModel(
    val name: String,
    val id: String,
    val date: String,
    val url: String?
) {
    companion object : LocalToUserMapper<UserLocalDataModel, UserDataModel> {
        override fun toUser(userLocalDataModel: UserLocalDataModel): UserDataModel =
            UserDataModel(
                name = userLocalDataModel.name,
                id = userLocalDataModel.id,
                url = userLocalDataModel.url,
                date = userLocalDataModel.date
            )

        override fun from(r: UserLocalDataModel?): UserDataModel {
            return UserDataModel(
                name = r?.name,
                id = r?.id,
                url = r?.url,
                date = r?.date
            )
        }
    }
}


