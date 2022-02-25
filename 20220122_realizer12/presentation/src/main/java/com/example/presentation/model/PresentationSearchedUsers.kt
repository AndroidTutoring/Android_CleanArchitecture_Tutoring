package com.example.presentation.model

import android.os.Parcelable
import com.example.data.model.SearchedUserDataModel
import com.example.data.model.SearchedUsersDataModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PresentationSearchedUsers(
    var items: ArrayList<PresentationSearchedUser>? = null,//검색된 유저 리스트
    var total_count: Int? = null//전체 카운트
) : Parcelable {
    companion object {

        //데이터 모듈 데이터 모델로
        fun toDataModel(
            presentationSearchedUsers: PresentationSearchedUsers
        ): SearchedUsersDataModel {
            return SearchedUsersDataModel(
                items = presentationSearchedUsers.items?.map {
                    PresentationSearchedUser.toDataModel(
                        it
                    )
                } as ArrayList<SearchedUserDataModel>,
                total_count = presentationSearchedUsers.total_count
            )
        }

        //presentation 모듈 데이터 모델로
        fun toPresentationModel(
            searchedUsers: SearchedUsersDataModel
        ): PresentationSearchedUsers {
            return PresentationSearchedUsers(
                items = searchedUsers.items?.map { PresentationSearchedUser.toPresentationModel(it) } as ArrayList<PresentationSearchedUser>,
                total_count = searchedUsers.total_count
            )
        }
    }
}