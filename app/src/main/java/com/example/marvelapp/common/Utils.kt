package com.example.marvelapp.common

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ImageView.loadImage(context : Context, url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}
