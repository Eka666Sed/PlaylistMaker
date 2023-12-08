package com.example.playlistmaker.ui.media.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MediaViewModel (
//аргумент *private val trackId: String,
    private val trackId: String,
): ViewModel(){

    // Основной код

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MediaViewModel(
                        trackId//аргумент *trackId
                    ) as T
                }
            }
    }

}