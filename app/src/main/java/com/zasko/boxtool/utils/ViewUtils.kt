package com.zasko.boxtool.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide


fun ImageView.loadImage(url: String, @DrawableRes placeholderRes: Int = 0) {
    Glide.with(this).load(url).let {
        if (placeholderRes != 0) {
            it.placeholder(placeholderRes)
        } else {
            it
        }
    }.centerCrop().into(this)
}