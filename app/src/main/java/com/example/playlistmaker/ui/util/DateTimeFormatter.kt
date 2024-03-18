package com.example.playlistmaker.ui.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateTimeFormatter {
    private const val MINUTES_PATTERN = "mm"

    fun millisToMinutes(millis: Long): String {
        return SimpleDateFormat(MINUTES_PATTERN, Locale.getDefault()).format(millis)
    }
}