package com.example.itstrending.Utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class ImageUtils {
    companion object {
        fun loadImage(
            context: Context,
            url: String,
            placeHolder: Int,
            imageView: ImageView,
            radius: Int
        ) {
            val requestOptions = RequestOptions().placeholder(placeHolder).centerCrop()
            Glide.with(context).load(url)
                .apply(requestOptions.transform(CenterCrop(), RoundedCorners(radius)))
                .into(imageView)
        }
    }
}