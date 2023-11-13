package com.example.playlistmaker.domain

import com.example.playlistmaker.data.entities.ResponseTrack
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.data.web_action.Repository
import com.example.playlistmaker.presentation.adapters.TrackHistoryAdapter
import retrofit2.Response

class GetWebDataUseCase(val apiDataSource: Repository) {
    fun getWebRequest(/*binding: ActivitySearchBinding, context: Context*/query:String)  : Response<ResponseTrack>?{
        apiDataSource.getWebRequest(/*binding, context*/query)
        if(apiDataSource.responseApi != null){
            return apiDataSource.responseApi
        }
        else{
            return null
        }
    }

    fun showHistoryRequest(trackAdapter: TrackHistoryAdapter, list: MutableList<Track>) {
        apiDataSource.showHistoryRequest(trackAdapter, list)
    }
}