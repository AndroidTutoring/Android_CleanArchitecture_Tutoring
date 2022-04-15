package com.example.data.model

import android.os.Parcelable
import com.example.domain.entity.SearchedUserEntity
import com.example.domain.entity.SearchedUsersEntity
import com.example.domain.entity.UserRepoEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRepoDataModel(
    var id: Long? = null,
    var full_name: String? = null,
    var url: String? = null,
    var stargazers_count: Int? = null
) : Parcelable {
    companion object{

        //데이터 모듈 데이터 모델로
        fun toEntity(
            userRepoDataModel: UserRepoDataModel
        ): UserRepoEntity {
            return UserRepoEntity(
               id = userRepoDataModel.id,
               full_name = userRepoDataModel.full_name,
               url = userRepoDataModel.url,
               stargazers_count = userRepoDataModel.stargazers_count
            )
        }

        fun toDataModel(
            userRepoEntity: UserRepoEntity
        ): UserRepoDataModel {
            return UserRepoDataModel(
                id = userRepoEntity.id,
                full_name = userRepoEntity.full_name,
                url = userRepoEntity.url,
                stargazers_count = userRepoEntity.stargazers_count
            )
        }

    }
}


