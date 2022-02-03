package com.example.presentation
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubRemoteRepo {
    @GET("users/{owner}/repos")
    fun getRepos(@Path("owner") owner: String)
            : Single<List<User>>
}