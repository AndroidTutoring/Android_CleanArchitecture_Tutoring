package com.example.local.source

import com.example.data.model.UserDataModel
import com.example.data.repository.githubSource.local.LocalDataSource
import com.example.data.repository.githubSource.remote.RemoteDataSource
import com.example.local.mapping.LocalToUserMapper
import com.example.local.model.UserLocalDataModel
import com.example.local.room.UserDao
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor (
    private val dao : UserDao,
    ) : LocalDataSource {

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