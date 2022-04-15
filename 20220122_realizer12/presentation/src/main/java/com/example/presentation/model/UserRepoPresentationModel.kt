package com.example.presentation.model

import android.os.Parcelable
import com.example.data.model.UserRepoDataModel
import com.example.domain.entity.SearchedUserEntity
import com.example.domain.entity.SearchedUsersEntity
import com.example.domain.entity.UserRepoEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRepoPresentationModel(
    var id: Long? = null,
    var full_name: String? = null,
    var url: String? = null,
    var stargazers_count: Int? = null
) : Parcelable {
    companion object {


        fun toEntity(
            presentationUserRepo: UserRepoPresentationModel
        ): UserRepoEntity {
            return UserRepoEntity(
                id = presentationUserRepo.id,
                full_name = presentationUserRepo.full_name,
                url = presentationUserRepo.url,
                stargazers_count = presentationUserRepo.stargazers_count
            )
        }


        //presentation 모듈 데이터 모델로
        fun toPresentationModel(
            userRepo: UserRepoEntity
        ): UserRepoPresentationModel {
            return UserRepoPresentationModel(
                id = userRepo.id,
                full_name = userRepo.full_name,
                url = userRepo.url,
                stargazers_count = userRepo.stargazers_count
            )
        }
    }
}

