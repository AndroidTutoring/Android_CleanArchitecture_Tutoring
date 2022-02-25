package com.example.presentation.model

import android.os.Parcelable
import com.example.data.model.SearchedUserDataModel
import com.example.data.model.SearchedUsersDataModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchedUsersPresentationModel(
    var items: ArrayList<SearchedUserPresentationModel>? = null,//검색된 유저 리스트
    var total_count: Int? = null//전체 카운트
) : Parcelable {
    companion object {

        //데이터 모듈 데이터 모델로
        fun toDataModel(
            presentationSearchedUsers: SearchedUsersPresentationModel
        ): SearchedUsersDataModel {
            return SearchedUsersDataModel(
                items = presentationSearchedUsers.items?.map {
                    SearchedUserPresentationModel.toDataModel(
                        it
                    )
                } as ArrayList<SearchedUserDataModel>,
                total_count = presentationSearchedUsers.total_count
            )
        }

        //presentation 모듈 데이터 모델로
        fun toPresentationModel(
            searchedUsers: SearchedUsersDataModel
        ): SearchedUsersPresentationModel {
            return SearchedUsersPresentationModel(
                items = searchedUsers.items?.map { SearchedUserPresentationModel.toPresentationModel(it) } as ArrayList<SearchedUserPresentationModel>,
                total_count = searchedUsers.total_count
            )
        }
    }
}