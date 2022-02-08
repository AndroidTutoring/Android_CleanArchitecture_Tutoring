package com.example.presentation.model

import android.os.Parcelable
import com.example.data.model.SearchedUser
import com.example.data.model.SearchedUsers
import kotlinx.parcelize.Parcelize

@Parcelize
data class PresentationSearchedUsers(
    var items: ArrayList<PresentationSearchedUser>? = null,//검색된 유저 리스트
    var total_count: Int? = null//전체 카운트
) : Parcelable {
    companion object{

        //데이터 모듈 데이터 모델로
        fun toDataModel(presentationSearchedUsers: PresentationSearchedUsers):SearchedUsers{
            return SearchedUsers(
                items = presentationSearchedUsers.items?.map { PresentationSearchedUser.toDataModel(it) } as ArrayList<SearchedUser>,
                total_count = presentationSearchedUsers.total_count
            )
        }

        //presentation 모듈 데이터 모델로
        fun toPresentationModel(searchedUsers: SearchedUsers):PresentationSearchedUsers{
            return PresentationSearchedUsers(
                items = searchedUsers.items?.map { PresentationSearchedUser.toPresentationModel(it) } as ArrayList<PresentationSearchedUser>,
                total_count = searchedUsers.total_count
            )
        }
    }
}