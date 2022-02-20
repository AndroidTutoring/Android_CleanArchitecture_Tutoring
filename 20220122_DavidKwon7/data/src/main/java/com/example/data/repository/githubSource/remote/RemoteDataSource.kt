package com.example.data.repository.githubSource.remote

import com.example.data.model.UserDataModel
import io.reactivex.Single

interface RemoteDataSource {
    fun getRepos(userName:String) : Single<List<UserDataModel>>


}