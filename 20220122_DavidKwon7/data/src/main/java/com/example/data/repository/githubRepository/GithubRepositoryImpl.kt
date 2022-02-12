package com.example.data.repository.githubRepository

import com.example.data.model.User
import com.example.data.repository.githubSource.local.LocalDataSource
import com.example.data.repository.githubSource.remote.RemoteDataSource
import com.example.presentation.User
import io.reactivex.Completable
import io.reactivex.Single

class GithubRepositoryImpl (
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource

    ) : GithubRepository {

        override fun getCachedUserList(): Single<List<User>> {
            return localDataSource.getCachedUserList()
        }

        override fun addFav(favoriteUser: User): Completable {
            return localDataSource.addFav(favoriteUser)
        }

        override fun deleteFav(deleteUser: User): Completable {
            return localDataSource.deleteFav(deleteUser)
        }


        override fun getRepos(): Single<List<User>> = remoteDataSource.getRepos(userName = String())
}