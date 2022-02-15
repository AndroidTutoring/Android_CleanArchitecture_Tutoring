package com.example.presentation.util

import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


//바인딩 어뎁터 모음

//리사이클러뷰에
@BindingAdapter("bind:addList")
fun <T, VH : RecyclerView.ViewHolder> RecyclerView.addList(list: List<T>?) {
    if (!list.isNullOrEmpty()) {
        (adapter as ListAdapter<T, VH>).submitList(list.toMutableList())
    }
}

//키보드 imeoption  action search 가능하게
@BindingAdapter("bind:searchOption")
fun EditText.search(doEvent: () -> Unit) {
    this.setOnEditorActionListener(object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doEvent()
                return true
            }
            return false
        }
    })
}


//값이 없으면  empty뷰 보여주고  있으면  안보여준다.
@BindingAdapter("bind:visibility")
fun <T> View.setVisible(list: List<T>?) {
    if (!list.isNullOrEmpty()) {
        this.visibility = View.GONE
    } else {
        this.visibility = View.VISIBLE
    }
}


