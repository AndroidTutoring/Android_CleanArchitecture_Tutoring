package com.example.domain.usecase

import com.example.domain.entity.SearchedUserEntity
import com.example.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class AddFavoriteUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    fun addFavoriteUsers(
        searchedUserEntity: SearchedUserEntity
    ):Completable{
        return userRepository.addFavoriteUser(searchedUserEntity).subscribeOn(Schedulers.io())
    }

}