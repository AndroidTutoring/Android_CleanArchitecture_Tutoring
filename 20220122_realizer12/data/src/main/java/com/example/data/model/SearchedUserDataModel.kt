package com.example.data.model

import android.os.Parcelable
import com.example.domain.entity.SearchedUserEntity
import kotlinx.parcelize.Parcelize


@Parcelize
data class SearchedUserDataModel(
    var uid: Long=0L,//데이터 고유식별값 -> auto increment
    var id: Long = 0L,//유저 아이디
    var avatar_url: String = "",//유저 프로필 url
    var login: String = "",//유저 닉네임
    var html_url: String = "",//유저 repo url
    var isMyFavorite: Boolean = false//유저 repo url
) : Parcelable {
    companion object{

        fun toDataModel(
            searchedUserEntity: SearchedUserEntity
        ):SearchedUserDataModel{
            return SearchedUserDataModel(
                uid = searchedUserEntity.uid,
                id = searchedUserEntity.id,
                avatar_url = searchedUserEntity.avatar_url,
                login = searchedUserEntity.login,
                html_url = searchedUserEntity.html_url,
                isMyFavorite = searchedUserEntity.isMyFavorite
            )
        }
    }
}
