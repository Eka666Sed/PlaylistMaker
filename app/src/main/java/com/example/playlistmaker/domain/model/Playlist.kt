package com.example.playlistmaker.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
data class Playlist(
    val id: String,
    val name: String,
    val description: String?,
    val coverUri: String?,
    val tracksIds: List<Long> = listOf(),
    val tracksCount: Int = 0
): Parcelable
