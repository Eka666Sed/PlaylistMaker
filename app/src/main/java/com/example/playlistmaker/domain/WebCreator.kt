package com.example.playlistmaker.domain

import com.example.playlistmaker.data.TracksRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.domain.api.TracksInteractor
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.impl.TracksInteractorImpl

object WebCreator {
    private fun getTracksRepository(): TracksRepository = TracksRepositoryImpl(RetrofitNetworkClient())
    fun provideTracksInteractor(): TracksInteractor = TracksInteractorImpl(getTracksRepository())
}