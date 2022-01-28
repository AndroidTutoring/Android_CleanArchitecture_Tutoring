package com.example.myapplication.model.userSearch


import com.google.gson.annotations.SerializedName

data class userSearch(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<Item>?,
    @SerializedName("total_count")
    val totalCount: Int?
)