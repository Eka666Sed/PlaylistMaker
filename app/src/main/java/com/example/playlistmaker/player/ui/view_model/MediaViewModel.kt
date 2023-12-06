package com.example.playlistmaker.player.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.search.ui.view_model.SearchViewModel

class MediaViewModel (
//аргумент *private val trackId: String,
): ViewModel(){

    // Основной код

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MediaViewModel(
                        //аргумент *trackId
                    ) as T
                }
            }
    }

}