package com.example.data.repository.githubRepository

import com.example.data.model.UserDataModel
import com.example.data.repository.githubSource.local.LocalDataSource
import com.example.data.repository.githubSource.remote.RemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class GithubRepositoryImpl (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource

    ) : GithubRepository {

        override fun getCachedUserList(): Single<List<UserDataModel>> {
            return localDataSource.getCachedUserList()
        }

        override fun addFav(favoriteUser: UserDataModel): Completable {
            return localDataSource.addFav(favoriteUser)
        }

        override fun deleteFav(deleteUser: com.example.data.model.UserDataModel): Completable {
            return localDataSource.deleteFav(deleteUser)
        }


        override fun getRepos(): Single<List<UserDataModel>> = remoteDataSource.getRepos(userName = String())
}