package com.example.remote.model

import android.os.Parcelable
import com.example.data.model.UserRepoDataModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRepoRemote(
    var id: Long? = null,
    var full_name: String? = null,
    var url: String? = null,
    var stargazers_count: Int? = null
) : Parcelable {
    companion object {

        //데이터 모듈 데이터 모델로
        fun toDataModel(
            userRepoRemote: UserRepoRemote
        ): UserRepoDataModel {
            return UserRepoDataModel(
                id = userRepoRemote.id,
                full_name = userRepoRemote.full_name,
                url = userRepoRemote.url,
                stargazers_count = userRepoRemote.stargazers_count
            )
        }
    }
}
