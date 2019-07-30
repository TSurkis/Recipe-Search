package com.tsurkis.recipesearch.custom.wrappers

import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ImageLoader @Inject constructor(private val imageLoaderInstance: Picasso) {

    fun loadImage(imageUrl: String, imageView: ImageView) {
        imageLoaderInstance
            .load(imageUrl)
            .centerCrop()
            .fit()
            .into(imageView)
    }
}