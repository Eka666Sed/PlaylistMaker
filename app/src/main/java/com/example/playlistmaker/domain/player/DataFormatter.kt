package com.example.playlistmaker.domain.player

import com.example.playlistmaker.data.player.Track
import java.text.SimpleDateFormat
import java.util.Locale

object DataFormatter {
    fun getFormattedTrackTime(track: Track): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
    }

    fun getYearFromReleaseDate(track: Track): String = track.releaseDate.substring(0, 4)

    fun getImageNeededSize(track: Track): String = track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
}