package com.example.presentation.model

import android.os.Parcelable
import com.example.data.model.SearchedUserDataModel
import com.example.domain.entity.SearchedUserEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchedUserPresentationModel(
    var uid: Long,//데이터 고유식별값 -> auto increment
    var id: Long = 0L,//유저 아이디
    var avatar_url: String = "",//유저 프로필 url
    var login: String = "",//유저 닉네임
    var html_url: String = "",//유저 repo url
    var isMyFavorite: Boolean = false//유저 repo url
) : Parcelable {
    companion object {

        fun toEntity(
            searchedUserDataModel: SearchedUserPresentationModel
        ): SearchedUserEntity {
            return SearchedUserEntity(
                uid = searchedUserDataModel.uid,
                id = searchedUserDataModel.id,
                avatar_url = searchedUserDataModel.avatar_url,
                login = searchedUserDataModel.login,
                html_url = searchedUserDataModel.html_url,
                isMyFavorite = searchedUserDataModel.isMyFavorite
            )
        }


        //presentation 모듈 데이터 모델로
        fun toPresentationModel(
            searchedUser: SearchedUserEntity
        ): SearchedUserPresentationModel {
            return SearchedUserPresentationModel(
                uid = searchedUser.uid,
                id = searchedUser.id,
                avatar_url = searchedUser.avatar_url,
                login = searchedUser.login,
                html_url = searchedUser.html_url,
                isMyFavorite = searchedUser.isMyFavorite
            )
        }
    }
}
