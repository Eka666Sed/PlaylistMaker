package com.example.playlistmaker.domain.utils

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormatter {
    private const val TIME_PATTERN = "mm:ss"

    fun formatMillisToString(timeMillis: Long): String =
        SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).format(timeMillis)

    fun getYearFromReleaseDate(date: String): String = date.substring(0, 4)
}