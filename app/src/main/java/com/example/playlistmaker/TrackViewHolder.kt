package com.example.playlistmaker

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class TrackViewHolder(track: View, private val context: Context) : RecyclerView.ViewHolder(track) {
    private val artistName: TextView = itemView.findViewById(R.id.tvArtistName)
    private val trackName: TextView = itemView.findViewById(R.id.tvTrackName)
    private val trackTime: TextView = itemView.findViewById(R.id.tvTrackTime)
    private val artworkUrl100: ImageView = itemView.findViewById(R.id.artworkUrl100)

    fun bind(track: Track) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        artistName.text = track.artistName
        trackName.text = track.trackName
        trackTime.text = track.getFormattedTrackTime()
        Glide.with(itemView)
            .applyDefaultRequestOptions(requestOptions)
            .load(track.artworkUrl100)
            .transform(CenterCrop(), RoundedCorners(dpToPx(radiusCorners, context)))
            .into(artworkUrl100)
    }

    private fun dpToPx(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
        ).toInt()
    }

    companion object {
        private const val radiusCorners = 2.0f
    }
}