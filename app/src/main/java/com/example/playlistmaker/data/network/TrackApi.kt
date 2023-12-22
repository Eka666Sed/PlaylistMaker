package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.search.model.ResponseTrack
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackApi {
    @GET("search?entity=song")
    fun searchTracks(@Query("term") text:String) : Call<ResponseTrack>
}