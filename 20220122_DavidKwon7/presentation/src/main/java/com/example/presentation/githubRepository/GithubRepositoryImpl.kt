package com.example.recylcerviewtest01.githubRepository

import android.util.Log
import com.example.presentation.User
import com.example.recylcerviewtest01.githubSource.local.LocalDataSource
import com.example.recylcerviewtest01.githubSource.remote.RemoteDataSource
import com.example.recylcerviewtest01.githubSource.remote.RemoteDataSourceImpl
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class GithubRepositoryImpl(
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

