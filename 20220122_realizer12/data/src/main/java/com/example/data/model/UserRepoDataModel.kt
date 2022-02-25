package com.example.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRepoDataModel(
    var id: Long? = null,
    var full_name: String? = null,
    var url: String? = null,
    var stargazers_count: Int? = null
) : Parcelable