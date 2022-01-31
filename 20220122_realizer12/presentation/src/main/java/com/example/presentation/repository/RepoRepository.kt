package com.example.presentation.repository

import com.example.presentation.model.UserRepo

interface RepoRepository {
    fun getUserRepoList(
        userName:String = "",
        onSuccess: (ArrayList<UserRepo>?) -> Unit,
        onFailure: (t:Throwable) -> Unit,
    )
}