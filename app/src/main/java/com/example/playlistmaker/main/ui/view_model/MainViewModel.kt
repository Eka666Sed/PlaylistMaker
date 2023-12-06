package com.example.playlistmaker.main.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.player.ui.view_model.MediaViewModel

class MainViewModel (
//аргумент *private val trackId: String,
): ViewModel(){

    // Основной код

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(
                        //аргумент *trackId
                    ) as T
                }
            }
    }
}

