package com.example.presentation.model

import android.os.Parcelable
import com.example.data.model.SearchedUserDataModel
import com.example.data.model.SearchedUsersDataModel
import com.example.domain.entity.SearchedUserEntity
import com.example.domain.entity.SearchedUsersEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchedUsersPresentationModel(
    var items: ArrayList<SearchedUserPresentationModel>? = null,//검색된 유저 리스트
    var total_count: Int? = null//전체 카운트
) : Parcelable {
    companion object {



        fun toEntity(
            presentationSearchedUsers: SearchedUsersPresentationModel
        ): SearchedUsersEntity {
            return SearchedUsersEntity(
               items =  presentationSearchedUsers.items?.map {
                   SearchedUserPresentationModel.toEntity(it)
               }as ArrayList<SearchedUserEntity>,

               total_count = presentationSearchedUsers.total_count
            )
        }


        //presentation 모듈 데이터 모델로
        fun toPresentationModel(
            searchedUsers: SearchedUsersEntity
        ): SearchedUsersPresentationModel {
            return SearchedUsersPresentationModel(
                items = searchedUsers.items?.map { SearchedUserPresentationModel.toPresentationModel(it) } as ArrayList<SearchedUserPresentationModel>,
                total_count = searchedUsers.total_count
            )
        }
    }
}