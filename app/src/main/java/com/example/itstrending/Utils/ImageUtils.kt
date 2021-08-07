package com.example.itstrending.Utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageUtils {
    companion object {
        fun loadImage(context: Context, url: String, placeHolder: Int, imageView: ImageView) {
            val requestOptions = RequestOptions().placeholder(placeHolder).centerCrop()
            Glide.with(context).load(url).apply(requestOptions).into(imageView)
        }
    }
}