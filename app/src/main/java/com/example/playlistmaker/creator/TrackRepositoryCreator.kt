package com.example.playlistmaker.creator

import android.content.Context
import com.example.playlistmaker.creator.SharedPreferencesCreator.createSharedPreferences
import com.example.playlistmaker.data.network.impl.RetrofitNetworkClient
import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.data.search.impl.TracksRepositoryImpl
import com.example.playlistmaker.data.utils.SharedPreferenceConverterImpl
import com.example.playlistmaker.domain.utils.SharedPreferenceConverter

object TrackRepositoryCreator {
    private var trackRepository: TracksRepositoryImpl? = null
    private var retrofitClient: RetrofitNetworkClient? = null
    private var sharedPreferencesConverter: SharedPreferenceConverter? = null

    fun createTracksRepository(context: Context): TracksRepository {
        return trackRepository ?: TracksRepositoryImpl(
            networkClient = retrofitClient ?: RetrofitNetworkClient().apply {
                retrofitClient = this
            },
            sharedPreferences = createSharedPreferences(context),
            sharedPreferencesConverter = createSharedPreferenceConverter()
        ).apply {
            trackRepository = this
        }
    }

    private fun createSharedPreferenceConverter(): SharedPreferenceConverter {
        return sharedPreferencesConverter ?: SharedPreferenceConverterImpl().apply {
            sharedPreferencesConverter = this
        }
    }
}