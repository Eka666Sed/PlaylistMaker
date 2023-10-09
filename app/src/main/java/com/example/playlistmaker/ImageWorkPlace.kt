package com.example.playlistmaker

import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

private const val radiusCorners = 2.0f

object ImageWorkPlace {
    fun getRecomendationImage(context: Context,bigSize:Boolean,view: ImageView,item:Track){

        Glide.with(context)
            .applyDefaultRequestOptions(getRequestOptions(bigSize))
            .load(item.getImageNeedSize())
            .transform(CenterCrop(), RoundedCorners(dpToPx(radiusCorners, context)))
            .into(view)
        Log.d("mes",item.getImageNeedSize())
    }
    private fun getRequestOptions(bigSize:Boolean) : RequestOptions{
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