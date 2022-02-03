package com.example.recylcerviewtest01.githubRepository

import android.util.Log
import com.example.recylcerviewtest01.Api
import com.example.recylcerviewtest01.User
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

    override fun addFav(): Completable {
        return localDataSource.addFav()
    }

    override fun deleteFav(): Completable {
        return localDataSource.deleteFav()
    }


    override fun getRepos(): Single<List<User>> {
        return remoteDataSource.getRepos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { Log.e("seungmin", "$it 발생") }
            .retry(2)
    }


}

