package com.example.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.data.model.SearchedUserDataModel
import kotlinx.parcelize.Parcelize

/**
 * Create Date: 2022/02/26
 *
 * @author leeDongHun
 * Description: 로컬 데이터 베이스 즐겨찾기 테이블 구성
 *
 * @see uid 데이터 고유식별값 -> auto increment
 * @see id 유저 아이디
 * @see avatar_url 유저 프로필 url
 * @see login 유저 닉네임
 * @see html_url 유저 repo url
 * @see isMyFavorite 유저 repo url
 * */

@Entity(
    tableName = "favoriteMarkTable",
    indices = [Index(value = ["user_id"], unique = true)]
)//id값은 깃헙 유저 아이디이므로, unique 설정
//깃헙 유저 모델
@Parcelize
data class SearchedUserLocal(
    @PrimaryKey(autoGenerate = true) var uid: Long,
    @ColumnInfo(name = "user_id") var id: Long = 0L,
    @ColumnInfo(name = "avatar_url") var avatar_url: String = "",
    @ColumnInfo(name = "user_nick_name") var login: String = "",
    @ColumnInfo(name = "repo_url") var html_url: String = "",
    @ColumnInfo(name = "is_my_favorite") var isMyFavorite: Boolean = false
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
