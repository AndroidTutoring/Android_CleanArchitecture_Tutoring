package com.example.presentation.util

import android.app.Activity
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

object Util {


    //키보드 imeoption  action search 가능하게
    fun EditText.search(view: View) {
        this.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    view.performClick()
                    return true
                }
                return false
            }
        })
    }


    //키보드 숨김
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //프래그먼트 확장용
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    //엑티비티 확장용
    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

}

