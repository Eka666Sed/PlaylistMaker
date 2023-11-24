package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.NetworkClient
import com.example.playlistmaker.data.dto.Response
import com.example.playlistmaker.data.dto.TracksSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {
    private val BASE_URL = "https://itunes.apple.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val create: ApiCollection = retrofit.create(ApiCollection::class.java)

    override fun doRequest(dto: Any): Response {
        if (dto is TracksSearchRequest) {
            try {
                val resp = create.search(dto.text).execute()
                val body = resp.body() ?: Response()
                return body.apply { resultCode = resp.code() }
            } catch (e: Throwable) {
                return Response().apply{ resultCode = -1 }
            }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}
