package com.example.playlistmaker.ui.util

import android.content.res.Resources
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R

private const val RADIUS_CORNERS = 8.0f

fun ImageView.load(imageUrl: String) {
    val requestOptions = RequestOptions()
        .placeholder(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    val density = Resources.getSystem().displayMetrics.density
    Glide.with(context)
        .applyDefaultRequestOptions(requestOptions)
        .load(imageUrl)
        .transform(CenterCrop(), RoundedCorners((RADIUS_CORNERS * density).toInt()))
        .into(this)
}