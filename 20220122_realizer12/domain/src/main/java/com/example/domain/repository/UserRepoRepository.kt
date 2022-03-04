package com.example.domain.repository

import com.example.domain.entity.UserRepoEntity
import io.reactivex.rxjava3.core.Single

interface UserRepoRepository {
    fun getUserRepoList(
        userName: String
    ): Single<List<UserRepoEntity>>
}