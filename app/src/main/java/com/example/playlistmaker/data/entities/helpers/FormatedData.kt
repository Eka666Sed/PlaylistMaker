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

    fun getYearFormReleaseDate(track: Track): String = track.releaseDate.substring(0, 4)

    fun getImageNeedSize(track: Track): String = track
        .artworkUrl100
        .replaceAfterLast('/', "512x512bb.jpg")
}