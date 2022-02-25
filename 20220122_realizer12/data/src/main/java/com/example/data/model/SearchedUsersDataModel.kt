package com.example.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//깃헙 유저 검색시  오는 리스트 받는 모델
@Parcelize
data class SearchedUsersDataModel(
    var items: ArrayList<SearchedUserDataModel>? = null,//검색된 유저 리스트
    var total_count: Int? = null//전체 카운트
) : Parcelable