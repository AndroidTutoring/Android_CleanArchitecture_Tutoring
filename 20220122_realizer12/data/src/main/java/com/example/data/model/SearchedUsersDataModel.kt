package com.example.data.model

import android.os.Parcelable
import com.example.domain.entity.SearchedUserEntity
import com.example.domain.entity.SearchedUsersEntity
import kotlinx.parcelize.Parcelize

//깃헙 유저 검색시  오는 리스트 받는 모델
@Parcelize
data class SearchedUsersDataModel(
    var items: ArrayList<SearchedUserDataModel>? = null,//검색된 유저 리스트
    var total_count: Int? = null//전체 카운트
) : Parcelable {
    companion object {

        //데이터 모듈 데이터 모델로
        fun toEntity(
            searchedUsersDataModel: SearchedUsersDataModel
        ): SearchedUsersEntity {
            return SearchedUsersEntity(
               items = searchedUsersDataModel.items?.map { SearchedUserDataModel.toEntity(it) } as ArrayList<SearchedUserEntity>,
               total_count = searchedUsersDataModel.total_count
            )
        }

        fun toDataModel(
            searchedUsersEntity: SearchedUsersEntity
        ): SearchedUsersDataModel {
            return SearchedUsersDataModel(
                items = searchedUsersEntity.items?.map { SearchedUserDataModel.toDataModel(it) } as ArrayList<SearchedUserDataModel>,
                total_count = searchedUsersEntity.total_count
            )
        }
    }
}