package com.example.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.data.model.SearchedUserDataModel
import kotlinx.parcelize.Parcelize

//로컬 데이터 베이스 즐겨찾기 테이블 구성
@Entity(
    tableName = "favoriteMarkTable",
    indices = [Index(value = ["user_id"], unique = true)]
)//id값은 깃헙 유저 아이디이므로, unique 설정
//깃헙 유저 모델
@Parcelize
data class SearchedUserLocal(
    @PrimaryKey(autoGenerate = true) var uid: Long,//데이터 고유식별값 -> auto increment
    @ColumnInfo(name = "user_id") var id: Long = 0L,//유저 아이디
    @ColumnInfo(name = "avatar_url") var avatar_url: String = "",//유저 프로필 url
    @ColumnInfo(name = "user_nick_name") var login: String = "",//유저 닉네임
    @ColumnInfo(name = "repo_url") var html_url: String = "",//유저 repo url
    @ColumnInfo(name = "is_my_favorite") var isMyFavorite: Boolean = false//유저 repo url
) : Parcelable {
    companion object {

        //데이터 모듈 데이터 모델로
        fun toDataModel(
            searchedUserLocal: SearchedUserLocal
        ): SearchedUserDataModel {
            return SearchedUserDataModel(
                uid = searchedUserLocal.uid,
                id = searchedUserLocal.id,
                avatar_url = searchedUserLocal.avatar_url,
                login = searchedUserLocal.login,
                html_url = searchedUserLocal.html_url,
                isMyFavorite = searchedUserLocal.isMyFavorite
            )
        }

        //데이터 모듈 데이터 모델로
        fun toLocalModel(
            searchedUser: SearchedUserDataModel
        ): SearchedUserLocal {
            return SearchedUserLocal(
                uid = searchedUser.uid,
                id = searchedUser.id,
                avatar_url = searchedUser.avatar_url,
                login = searchedUser.login,
                html_url = searchedUser.html_url,
                isMyFavorite = searchedUser.isMyFavorite
            )
        }
    }
}
