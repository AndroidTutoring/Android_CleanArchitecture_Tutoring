package com.example.domain.usecase

import com.example.domain.entity.SearchedUsersEntity
import com.example.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetSearchedUsersListUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun getSearchedUsers(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Single<SearchedUsersEntity> {
        return userRepository.getSearchUsers(searchQuery, page, perPage)
            .subscribeOn(Schedulers.io())
    }
}