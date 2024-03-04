package com.example.playlistmaker.data.external_storage

import android.net.Uri

interface ExternalStorageRepository {
    suspend fun savePlaylistCover(playlistId: String, uri: Uri): String
}