package com.example.playlistmaker

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TrackApiService {
    private const val BASE_URL = "https://itunes.apple.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val create: ApiCollection = retrofit.create(ApiCollection::class.java)
}