package com.example.playlistmaker.ui.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R
import com.example.playlistmaker.domain.model.Track



private const val IMAGE_SIZE = "512x512bb.jpg"


fun ImageView.load(imageUrl: String?, increaseQuality: Boolean = false) {
    val requestOptions = RequestOptions()
        .placeholder(R.drawable.placeholder)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    val roundingCorners = resources.getDimensionPixelSize(R.dimen.corner_radius)
    val newImageUrl = if (increaseQuality) imageUrl?.replaceAfterLast('/', IMAGE_SIZE) else imageUrl
    Glide.with(context)
        .applyDefaultRequestOptions(requestOptions)
        .load(newImageUrl)
        .transform(CenterCrop(), RoundedCorners(roundingCorners))
        .into(this)
}

fun List<Track>.calculateTotalDuration(): Int {
    val totalDurationInMillis = this.sumOf { it.trackTimeMillis }
    return DateTimeFormatter.millisToMinutes(totalDurationInMillis).toInt()
}