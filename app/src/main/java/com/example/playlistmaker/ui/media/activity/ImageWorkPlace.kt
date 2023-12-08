package com.example.playlistmaker.ui.media.activity

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.playlistmaker.R
import com.example.playlistmaker.data.player.Track
import com.example.playlistmaker.domain.player.DataFormatter

object ImageWorkPlace {
    private const val radiusCorners = 2.0f
    fun getRecomendationImage(context: Context, bigSize:Boolean, view: ImageView, item: Track){

        Glide.with(context)
            .applyDefaultRequestOptions(getRequestOptions(bigSize))
            .load(DataFormatter.getImageNeededSize(item))
            .transform(CenterCrop(), RoundedCorners(dpToPx(radiusCorners, context)))
            .into(view)
    }
    private fun getRequestOptions(bigSize:Boolean) : RequestOptions {
        return if(bigSize){
            RequestOptions()
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        } else{
            RequestOptions()
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
        }
    }
    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
        ).toInt()
    }
}