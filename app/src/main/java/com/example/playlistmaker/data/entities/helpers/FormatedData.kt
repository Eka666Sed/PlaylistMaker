package com.example.playlistmaker.data.entities.helpers

import com.example.playlistmaker.data.entities.Track
import java.text.SimpleDateFormat
import java.util.Locale

object FormatedData {
    fun getFormattedTrackTime(track: Track): String {
        return SimpleDateFormat(
            "mm:ss", Locale.getDefault()).format(track.trackTimeMillis
        )
    }

    fun Track.getYearFromReleaseDate(): String = this.releaseDate.substring(0, 4)

    fun Track.getImageNeedSize(): String = this
        .artworkUrl100
        .replaceAfterLast('/', "512x512bb.jpg")
}