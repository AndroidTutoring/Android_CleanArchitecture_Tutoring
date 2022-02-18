package com.example.presentation.util

import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


//바인딩 어뎁터 모음

//리사이클러뷰에
@BindingAdapter("bind:addList")
fun <T, VH : RecyclerView.ViewHolder> RecyclerView.addList(list: List<T>?) {
    if (!list.isNullOrEmpty()) {
        (adapter as ListAdapter<T, VH>).submitList(list.toMutableList())
    }else{
        //리스트가 아무것도 없을때는 submitlist 초기화
        (adapter as ListAdapter<T, VH>?)?.submitList(null)
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

//이미지로드용
@BindingAdapter("bind:loadImage","bind:loadError")
fun ImageView.loadImage(imageResource:Any?,errorImageResource:Any?){
    //즐겨찾기 true일때  별을 색칠해준다.
    Glide.with(context)
        .load(imageResource)
        .error(errorImageResource)
        .into(this)
}



//값이 없으면  empty뷰 보여주고  있으면  안보여준다.
@BindingAdapter("bind:visibility")
fun <T> View.setVisible(list: List<T>?) {
    this.isVisible = list.isNullOrEmpty()
}


