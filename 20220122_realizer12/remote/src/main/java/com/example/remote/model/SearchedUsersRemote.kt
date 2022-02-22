package com.example.remote.model

import android.os.Parcelable
import com.example.data.model.SearchedUser
import com.example.data.model.SearchedUsers
import kotlinx.parcelize.Parcelize

//깃헙 유저 검색시  오는 리스트 받는 모델
@Parcelize
data class SearchedUsersRemote(
    var items: ArrayList<SearchedUserRemote>? = null,//검색된 유저 리스트
    var total_count: Int? = null//전체 카운트
) : Parcelable {
    companion object {

        //데이터 모듈 데이터 모델로
        fun toDataModel(
            searchedUsersRemote: SearchedUsersRemote
        ): SearchedUsers {
            return SearchedUsers(
                items = searchedUsersRemote.items?.map {
                    SearchedUserRemote.toDataModel(
                        it
                    )
                } as ArrayList<SearchedUser>,
                total_count = searchedUsersRemote.total_count
            )
        }
    }
}
