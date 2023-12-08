package com.example.playlistmaker.data.search

import com.example.playlistmaker.data.player.ResponseTrack
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCollection {
    @GET("search?entity=song")
    fun search(@Query("term") text:String) : Call<ResponseTrack>
}