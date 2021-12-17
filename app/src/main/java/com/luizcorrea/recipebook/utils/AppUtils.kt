package com.luizcorrea.recipebook.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

class AppUtils {
    companion object {
        fun hideKeyboard(context: Activity) {
            val view = context.currentFocus
            view?.let { v ->
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(v.windowToken, 0)
            }
        }

    }
}