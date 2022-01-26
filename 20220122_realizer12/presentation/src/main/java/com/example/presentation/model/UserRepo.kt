package com.example.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRepo(
    var id:Long?=null,
    var full_name:String?=null,
    var url:String?=null,
    var stargazers_count:Int?=null
): Parcelable
