package com.example.domain.entity

data class UserRepoEntity (
    var id: Long? = null,
    var full_name: String? = null,
    var url: String? = null,
    var stargazers_count: Int? = null
)