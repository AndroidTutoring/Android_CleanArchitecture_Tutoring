package com.example.domain.entity

data class SearchedUsersEntity(
    var items: ArrayList<SearchedUserEntity>? = null,//검색된 유저 리스트
    var total_count: Int? = null//전체 카운트
)
