package com.example.presentation.repository

import com.example.presentation.model.SearchedUser
import com.example.presentation.model.SearchedUsers
import retrofit2.Call
import retrofit2.Callback

interface UserRepository {
    fun deleteFavoriteUser(vararg favoriteUser: SearchedUser)//유저 즐겨찾기에서 지우기
    fun addFavoriteUser(vararg favoriteUser: SearchedUser)//유저 즐겨찾기에 추가
    fun getFavoriteUsers():List<SearchedUser>?//즐겨 찾기 유저 다 가져오기
    fun getSearchUsers(
        query:String = "",
        page:Int,
        perPage:Int,
        onSuccess: (SearchedUsers?) -> Unit,
        onFailure: (call: Call<SearchedUsers>, callback: Callback<SearchedUsers>,errorCode:Int?) -> Unit,
    )
}