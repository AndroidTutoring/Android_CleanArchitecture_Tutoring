package com.example.domain.usecase

import com.example.domain.entity.SearchedUserEntity
import com.example.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class GetSearchedUsersListInMainUseCase @Inject constructor(
    private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase,
    private val getSearchedUsersListUseCase: GetSearchedUsersListUseCase
) {
    fun searchUsers(
        searchQuery: String,
        page: Int,
        perPage: Int
    ): Single<ArrayList<SearchedUserEntity>> {

        return Single.zip(
            getSearchedUsersListUseCase.getSearchedUsers(
                searchQuery,
                page,
                perPage
            ).retry(2),
            getFavoriteUsersUseCase.getFavoriteUsers(), { remote, local ->
                remote.items?.let { searchedUserList ->
                    searchedUserList.onEach { remote ->
                        if (local.any { it.id == remote.id }) {
                            remote.isMyFavorite = true
                        }
                    }
                }
            })
    }


}