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
private const val IMAGE_SIZE = "512x512bb.jpg"

fun ImageView.load(imageUrl: String, increaseQuality: Boolean = false) {
    val requestOptions = RequestOptions()
        .placeholder(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    val density = Resources.getSystem().displayMetrics.density
    val newImageUrl = if (increaseQuality) imageUrl.replaceAfterLast('/', IMAGE_SIZE) else imageUrl
    Glide.with(context)
        .applyDefaultRequestOptions(requestOptions)
        .load(newImageUrl)
        .transform(CenterCrop(), RoundedCorners((RADIUS_CORNERS * density).toInt()))
        .into(this)
}