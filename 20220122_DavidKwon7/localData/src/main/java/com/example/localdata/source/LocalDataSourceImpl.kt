package com.example.localdata.source

import com.example.data.model.UserDataModel
import com.example.data.repository.githubSource.local.LocalDataSource
import com.example.localdata.mapping.LocalToUserMapper
import com.example.localdata.model.UserLocalDataModel
import com.example.localdata.room.UserDao
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val dao : UserDao,
    private val userMapper: LocalToUserMapper<UserLocalDataModel, UserDataModel>
) : LocalDataSource {
    override fun getCachedUserList(): Single<List<UserDataModel>> {
        return dao.loadUserList()
    }

    override fun addFav(favoriteUser: UserDataModel): Completable {
        val userLocalDataModel = userMapper.to(favoriteUser)
        return dao.addFav(localUser = userLocalDataModel)
    }

    override fun deleteFav(deleteUser: UserDataModel): Completable {
        val userLocalDataModel = userMapper.to(deleteUser)
        return dao.deleteFav(user = userLocalDataModel)
    }


}