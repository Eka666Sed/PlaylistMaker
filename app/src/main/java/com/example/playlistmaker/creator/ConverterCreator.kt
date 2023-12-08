package com.example.playlistmaker.creator

import com.example.playlistmaker.data.setting.impl.SharedPreferenceConverterImpl
import com.example.playlistmaker.domain.settings.model.SharedPreferenceConverterInteractor
import com.example.playlistmaker.domain.settings.impl.SharedPreferenceConverterInteractorImpl

object ConverterCreator {
    fun sharedPreferenceConverter(): SharedPreferenceConverterInteractor =
        SharedPreferenceConverterInteractorImpl(SharedPreferenceConverterImpl())
}