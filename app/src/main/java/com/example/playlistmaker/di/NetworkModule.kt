package com.example.playlistmaker.di

import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.network.TrackApi
import com.example.playlistmaker.data.network.impl.RetrofitNetworkClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://itunes.apple.com/"

val networkModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(60, TimeUnit.SECONDS).build())
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