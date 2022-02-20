package com.example.data.repository.githubSource.local

import com.example.data.model.UserDataModel
import com.example.presentation.UserDao
import io.reactivex.Completable
import io.reactivex.Single

class LocalDataSourceImpl (private val dao : UserDao) : LocalDataSource {
    override fun getCachedUserList(): Single<List<UserDataModel>> {
        return dao.loadUserList()
    }

    override fun addFav(favoriteUser: UserDataModel): Completable {
        return dao.addFav(favoriteUser)
    }

    override fun deleteFav(deleteUser: UserDataModel): Completable {
        return dao.deleteFav(deleteUser)
    }


}