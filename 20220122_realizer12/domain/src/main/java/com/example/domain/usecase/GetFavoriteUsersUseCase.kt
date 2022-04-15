package com.example.domain.usecase

import com.example.domain.entity.SearchedUserEntity
import com.example.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetFavoriteUsersUseCase@Inject constructor(
    private val userRepository: UserRepository
) {
    fun getFavoriteUsers(): Single<List<SearchedUserEntity>> {
        return userRepository.getFavoriteUsers().subscribeOn(Schedulers.io())
    }
}