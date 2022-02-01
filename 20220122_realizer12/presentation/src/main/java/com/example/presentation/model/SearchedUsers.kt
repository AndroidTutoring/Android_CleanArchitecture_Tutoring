package com.example.presentation.model

import android.os.Parcelable
import io.reactivex.rxjava3.core.SingleSource
import kotlinx.parcelize.Parcelize

//깃헙 유저 검색시  오는 리스트 받는 모델
@Parcelize
data class SearchedUsers(
    var items:ArrayList<SearchedUser>? = null,//검색된 유저 리스트
    var total_count:Int? = null//전체 카운트
):Parcelable
