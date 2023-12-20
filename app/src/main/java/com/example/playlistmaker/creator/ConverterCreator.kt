package com.example.playlistmaker.creator

import com.example.playlistmaker.data.utils.SharedPreferenceConverterImpl
import com.example.playlistmaker.domain.utils.SharedPreferenceConverter

object ConverterCreator {
    private var sharedPreferencesConverter: SharedPreferenceConverter? = null

    fun createSharedPreferenceConverter(): SharedPreferenceConverter {
        return sharedPreferencesConverter ?: SharedPreferenceConverterImpl().apply {
            sharedPreferencesConverter = this
        }
    }
}