package com.tsurkis.recipesearch.custom.wrappers

import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageLoader private constructor() {

    companion object {
        val instance = ImageLoader()
    }

    private val picassoInstance: Picasso = Picasso.get()

    fun loadImage(imageUrl: String, imageView: ImageView) {
        picassoInstance
            .load(imageUrl)
            .centerCrop()
            .fit()
            .into(imageView)
    }
}