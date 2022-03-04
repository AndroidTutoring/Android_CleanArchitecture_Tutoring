package com.example.domain.usecase

import com.example.domain.entity.SearchedUserEntity
import com.example.domain.entity.SearchedUsersEntity
import com.example.domain.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetInitialSearchedUserUseCase @Inject constructor(
    private val getSearchedUsersListUseCase: GetSearchedUsersListUseCase
) {
    fun getSearchedUsers(): Single<List<SearchedUserEntity>> {
        return Single.zip(
            getSearchedUsersListUseCase.getSearchedUsers(
                searchQuery = "realizer12",
                page = 1,
                perPage = 10
            ).subscribeOn(Schedulers.io())
                .retryWhen { errorObservable ->
                    errorObservable
                        .delay(3, TimeUnit.SECONDS)
                        .take(2)
                },
            Single.timer(2, TimeUnit.SECONDS),
            { searchedUsers, _ ->
                return@zip searchedUsers.items as ArrayList<SearchedUserEntity>
            })
    }

}

