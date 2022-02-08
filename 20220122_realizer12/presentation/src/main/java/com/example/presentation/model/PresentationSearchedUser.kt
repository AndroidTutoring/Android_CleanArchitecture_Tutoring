package com.example.presentation.model

import android.os.Parcelable
import com.example.data.model.SearchedUser
import kotlinx.parcelize.Parcelize

@Parcelize
data class PresentationSearchedUser(
    var uid: Long,//데이터 고유식별값 -> auto increment
    var id: Long = 0L,//유저 아이디
    var avatar_url: String = "",//유저 프로필 url
    var login: String = "",//유저 닉네임
    var html_url: String = "",//유저 repo url
    var isMyFavorite: Boolean = false//유저 repo url
) : Parcelable {
    companion object {

        //데이터 모듈 데이터 모델로
        fun toDataModel(
            presentationSearchedUser: PresentationSearchedUser
        ): SearchedUser {
            return SearchedUser(
                uid = presentationSearchedUser.uid,
                id = presentationSearchedUser.id,
                avatar_url = presentationSearchedUser.avatar_url,
                login = presentationSearchedUser.login,
                html_url = presentationSearchedUser.html_url,
                isMyFavorite = presentationSearchedUser.isMyFavorite
            )
        }

        //presentation 모듈 데이터 모델로
        fun toPresentationModel(
            searchedUser: SearchedUser
        ): PresentationSearchedUser {
            return PresentationSearchedUser(
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
