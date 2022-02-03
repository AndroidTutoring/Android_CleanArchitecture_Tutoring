package com.example.recylcerviewtest01.githubSource.local

import com.example.presentation.User
import com.example.presentation.UserDao
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable

class LocalDataSourceImpl(private val dao : UserDao) : LocalDataSource {
    override fun getCachedUserList(): Single<List<User>> {
        return dao.loadUserList()
    }

    override fun addFav() : Completable {
        return dao.addFav(user = User("null","null","null","null"))
    }

    override fun deleteFav() : Completable{
        return dao.deleteFav(user = User("null","null","null","null"))
    }



}