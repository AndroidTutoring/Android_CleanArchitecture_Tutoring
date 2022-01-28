package com.example.myapplication.model.userSearch

import com.google.gson.annotations.SerializedName

data class userSearchModel(
    @SerializedName("id")
                           val id: Int?,
    @SerializedName("node_id")
    val nodeId: String?
    )
