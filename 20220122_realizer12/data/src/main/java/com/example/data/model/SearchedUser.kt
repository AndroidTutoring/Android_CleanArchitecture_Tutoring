package com.example.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

//로컬 데이터 베이스 즐겨찾기 테이블 구성
@Entity(
    tableName = "favoriteMarkTable",
    indices = [Index(value = ["user_id"], unique = true)]
)//id값은 깃헙 유저 아이디이므로, unique 설정
//깃헙 유저 모델
@Parcelize
data class SearchedUser(
    @PrimaryKey(autoGenerate = true) var uid: Long,//데이터 고유식별값 -> auto increment
    @ColumnInfo(name = "user_id") var id: Long = 0L,//유저 아이디
    @ColumnInfo(name = "avatar_url") var avatar_url: String = "",//유저 프로필 url
    @ColumnInfo(name = "user_nick_name") var login: String = "",//유저 닉네임
    @ColumnInfo(name = "repo_url") var html_url: String = "",//유저 repo url
    @ColumnInfo(name = "is_my_favorite") var isMyFavorite: Boolean = false//유저 repo url
) : Parcelable
