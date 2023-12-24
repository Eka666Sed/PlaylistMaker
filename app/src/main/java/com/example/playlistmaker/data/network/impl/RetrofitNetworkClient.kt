package com.example.playlistmaker.data.network.impl

import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.network.Response
import com.example.playlistmaker.data.network.TrackApi
import com.example.playlistmaker.data.search.model.TracksSearchRequest

class RetrofitNetworkClient (private val trackApi: TrackApi): NetworkClient {

    override fun doRequest(dto: Any): Response {
        return if (dto is TracksSearchRequest) {
            try {
                val response = trackApi.searchTracks(dto.text).execute()
                response.body()?.apply {
                    resultCode = response.code()
                } ?: Response(resultCode = response.code())
            } catch (e: Throwable) {
                Response(resultCode = -1)
            }
        } else {
            Response(resultCode = 400)
        }
    }
}
