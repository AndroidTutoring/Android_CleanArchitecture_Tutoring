package com.example.presentation.app

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.UserDataModel
import com.example.presentation.ProfileAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("bind:setProgressBar")
    fun setProgressBar(view:View, isVisible:Boolean){
        view.visibility=if (isVisible)View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("addList")
    fun RecyclerView.addList(list: List<UserDataModel>){
        (adapter as ProfileAdapter).addItem(list)
    }
}