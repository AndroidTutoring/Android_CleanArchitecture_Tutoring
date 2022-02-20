package com.example.presentation

import android.view.View
import com.example.data.model.UserDataModel

interface OnItemClickListener{
    fun onItemClick(v: View, data: UserDataModel, pos:Int)
}