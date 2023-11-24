package com.example.playlistmaker.domain

import com.example.playlistmaker.data.utils.local_storage.SharedPreferenceConverterImpl
import com.example.playlistmaker.domain.utils.SharedPreferenceConverterInteractor
import com.example.playlistmaker.domain.utils.SharedPreferenceConverterInteractorImpl

object ConverterCreator {
    fun sharedPreferenceConverter(): SharedPreferenceConverterInteractor =
        SharedPreferenceConverterInteractorImpl(SharedPreferenceConverterImpl())
}