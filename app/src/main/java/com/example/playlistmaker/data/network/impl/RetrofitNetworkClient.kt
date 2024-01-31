package com.example.playlistmaker.data.network.impl

import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.network.Response
import com.example.playlistmaker.data.network.TrackApi
import com.example.playlistmaker.data.search.model.TracksSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient (private val trackApi: TrackApi): NetworkClient {

    override suspend fun doRequest(dto: Any): Response {
        return if (dto is TracksSearchRequest) {
            withContext(Dispatchers.IO) {
                try {
                    trackApi.searchTracks(dto.text).apply {
                        resultCode = 200
                    }
                } catch (e: Throwable) {
                    Response(resultCode = -1)
                }
            }
        } else {
            Response(resultCode = 400)
        }
    }
}
