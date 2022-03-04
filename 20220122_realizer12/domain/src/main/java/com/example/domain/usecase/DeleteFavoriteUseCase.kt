package com.example.domain.usecase

import com.example.domain.entity.SearchedUserEntity
import com.example.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    fun deleteFavoriteUsers(
        searchedUserEntity: SearchedUserEntity
    ): Completable {
        return userRepository.deleteFavoriteUser(searchedUserEntity)
            .subscribeOn(Schedulers.io())
    }


}