package com.example.playlistmaker.search.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.settings.ui.view_model.SettingsViewModel

class SearchViewModel (
//аргумент *private val trackId: String,
): ViewModel(){

    // Основной код

    companion object {
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(
                        //аргумент *trackId
                    ) as T
                }
            }
    }
}