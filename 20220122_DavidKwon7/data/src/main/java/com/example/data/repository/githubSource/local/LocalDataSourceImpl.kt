package com.example.data.repository.githubSource.local

import com.example.presentation.User
import com.example.presentation.UserDao
import io.reactivex.Completable
import io.reactivex.Single

class LocalDataSourceImpl (private val dao : UserDao) : LocalDataSource {
    override fun getCachedUserList(): Single<List<User>> {
        return dao.loadUserList()
    }

    override fun addFav(favoriteUser: User): Completable {
        return dao.addFav(favoriteUser)
    }

    override fun deleteFav(deleteUser: User): Completable {
        return dao.deleteFav(deleteUser)
    }


}