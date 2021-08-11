package com.example.itstrending.Utils

import android.content.Context
import android.widget.Toast

class ViewUtils {
    companion object {
        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}