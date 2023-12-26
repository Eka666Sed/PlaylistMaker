package com.example.playlistmaker.di

import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.network.TrackApi
import com.example.playlistmaker.data.network.impl.RetrofitNetworkClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://itunes.apple.com/"

val networkModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<TrackApi> {
        get<Retrofit>().create(TrackApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(trackApi = get())
    }
}