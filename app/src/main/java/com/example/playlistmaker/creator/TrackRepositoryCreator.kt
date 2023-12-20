package com.example.playlistmaker.creator

import android.content.Context
import com.example.playlistmaker.creator.SharedPreferencesCreator.createSharedPreferences
import com.example.playlistmaker.data.network.impl.RetrofitNetworkClient
import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.data.search.impl.TracksRepositoryImpl

object TrackRepositoryCreator {
    private var trackRepository: TracksRepositoryImpl? = null
    private var retrofitClient: RetrofitNetworkClient? = null

    fun createTracksRepository(context: Context): TracksRepository {
        return trackRepository ?: TracksRepositoryImpl(
            networkClient = retrofitClient ?: RetrofitNetworkClient().apply {
                retrofitClient = this
            },
            sharedPreferences = createSharedPreferences(context),
            sharedPreferencesConverter = ConverterCreator.createSharedPreferenceConverter()
        ).apply {
            trackRepository = this
        }
    }
}