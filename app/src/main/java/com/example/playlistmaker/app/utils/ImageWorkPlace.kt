package com.example.playlistmaker.app.utils

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R

object ImageWorkPlace {
    private const val RADIUS_CORNERS = 2.0f
    private const val IMAGE_SIZE = "512x512bb.jpg"
    fun getRecommendationImage(context: Context, view: ImageView, imageUrl: String){

        Glide.with(context)
            .applyDefaultRequestOptions(getRequestOptions())
            .load(getImageNeededSize(imageUrl))
            .transform(CenterCrop(), RoundedCorners(dpToPx(context)))
            .into(view)
    }
    private fun getRequestOptions() : RequestOptions {
        return RequestOptions()
            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }
    private fun dpToPx(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, RADIUS_CORNERS, context.resources.displayMetrics
        ).toInt()
    }

    private fun getImageNeededSize(url: String): String = url.replaceAfterLast('/', IMAGE_SIZE)
}