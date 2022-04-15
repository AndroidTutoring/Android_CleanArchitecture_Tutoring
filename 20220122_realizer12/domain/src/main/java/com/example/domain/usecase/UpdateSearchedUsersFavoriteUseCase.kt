package com.example.domain.usecase

import com.example.domain.entity.SearchedUserEntity
import com.example.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class UpdateSearchedUsersFavoriteUseCase@Inject constructor(
    private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase
) {

    fun updateFavoriteValue(searchedUserList: List<SearchedUserEntity>): Single<List<SearchedUserEntity>>{
        return getFavoriteUsersUseCase.getFavoriteUsers()
            .subscribeOn(Schedulers.io())
            .map { favoriteUsers->
                searchedUserList.onEach { searchedUser->
                    if(favoriteUsers.any { it.id == searchedUser.id }){
                        searchedUser.isMyFavorite = true
                        searchedUser.uid = favoriteUsers.find { it.id == searchedUser.id }?.uid ?:0
                    }
                }
            }
    }


}