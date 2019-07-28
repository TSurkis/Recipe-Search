package com.tsurkis.recipesearch.custom.wrappers

import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageLoader(private val imageLoaderInstance: Picasso) {

    fun loadImage(imageUrl: String, imageView: ImageView) {
        imageLoaderInstance
            .load(imageUrl)
            .centerCrop()
            .fit()
            .into(imageView)
    }
}