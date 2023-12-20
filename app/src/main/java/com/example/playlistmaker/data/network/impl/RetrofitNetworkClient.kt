package com.example.playlistmaker.data.network.impl

import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.network.Response
import com.example.playlistmaker.data.network.TrackApi
import com.example.playlistmaker.data.search.model.TracksSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {
    private val BASE_URL = "https://itunes.apple.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: TrackApi = retrofit.create(TrackApi::class.java)

    override fun doRequest(dto: Any): Response {
        return if (dto is TracksSearchRequest) {
            try {
                val response = api.searchTracks(dto.text).execute()
                val body = response.body() ?: Response()
                body.apply { resultCode = response.code() }
            } catch (e: Throwable) {
               Response().apply{ resultCode = -1 }
            }
        } else {
             Response().apply { resultCode = 400 }
        }
    }
}
