package com.example.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//깃헙 유저 모델
@Parcelize
data class SearchedUser(
    var id:Long? = null,//유저 아이디
    var avatar_url:String? = null,//유저 프로필 url
    var login:String? = null,//유저 닉네임
    var html_url:String? = null//유저 repo url
):Parcelable
