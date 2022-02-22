package com.example.remote.model

import android.os.Parcelable
import com.example.data.model.SearchedUser
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchedUserRemote(
    var uid: Long,//데이터 고유식별값 -> auto increment
    var id: Long = 0L,//유저 아이디
    var avatar_url: String = "",//유저 프로필 url
    var login: String = "",//유저 닉네임
    var html_url: String = "",//유저 repo url
    var isMyFavorite: Boolean = false//유저 repo url
) : Parcelable{
    companion object {

        //데이터 모듈 데이터 모델로
        fun toDataModel(
            searchedUserRemote: SearchedUserRemote
        ): SearchedUser {
            return SearchedUser(
                uid = searchedUserRemote.uid,
                id = searchedUserRemote.id,
                avatar_url = searchedUserRemote.avatar_url,
                login = searchedUserRemote.login,
                html_url = searchedUserRemote.html_url,
                isMyFavorite = searchedUserRemote.isMyFavorite
            )
        }
    }
}

