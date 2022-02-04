package com.example.presentation

import android.view.View

interface OnItemClickListener{
    fun onItemClick(v: View, data : User, pos:Int)
}