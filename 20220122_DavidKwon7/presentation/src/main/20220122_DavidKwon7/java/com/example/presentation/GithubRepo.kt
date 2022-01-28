package com.example.recylcerviewtest01

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class GithubRepo(
    @SerializedName("name") val name: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("created_at") val date: String?,
    @SerializedName("html_url") val url: String?
) : Parcelable {

    constructor(parcel: Parcel,) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel?, flags: Int) {
        parcel?.writeString(name)
        parcel?.writeString(id)
        parcel?.writeString(date)
        parcel?.writeString(url)
    }

    companion object CREATOR : Parcelable.Creator<GithubRepo> {
        override fun createFromParcel(parcel: Parcel): GithubRepo {
            return GithubRepo(parcel)
        }

        override fun newArray(size: Int): Array<GithubRepo?> {
            return arrayOfNulls(size)
        }
    }
}
