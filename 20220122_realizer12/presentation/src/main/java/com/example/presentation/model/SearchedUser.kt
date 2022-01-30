package com.example.presentation.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//로컬 데이터 베이스 즐겨찾기 테이블 구성
@Entity(
    tableName = "favoriteMarkTable",
    indices = [Index(value = ["userId"], unique = true)]
)//id값은 깃헙 유저 아이디이므로, unique 설정
//깃헙 유저 모델
@Parcelize
data class SearchedUser(
    @PrimaryKey(autoGenerate = true) var uid: Long,//데이터 고유식별값 -> auto increment
    @ColumnInfo(name = "userId") var id: Long? = null,//유저 아이디
    @ColumnInfo(name = "avatarUrl") var avatar_url: String? = null,//유저 프로필 url
    @ColumnInfo(name = "userNickName") var login: String? = null,//유저 닉네임
    @ColumnInfo(name = "repoUrl") var html_url: String? = null,//유저 repo url
    @ColumnInfo(name = "isMyFavorite") var isMyFavorite: Boolean = false//유저 repo url
) : Parcelable
