package com.example.playlistmaker.data

import com.example.playlistmaker.data.dto.Response
import com.example.playlistmaker.data.dto.ResponseTrack
import com.example.playlistmaker.data.dto.TracksSearchRequest
import com.example.playlistmaker.domain.api.TracksRepository
import com.example.playlistmaker.domain.models.Track

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {
    private lateinit var resultRequest: Response
    override fun searchTracks(text: String): List<Track>{
        val response = networkClient.doRequest(TracksSearchRequest(text))
        if (response.resultCode == 200) {
            resultRequest = response
            return (response as ResponseTrack).results.map {
                Track(
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.primaryGenreName,
                    it.collectionName,
                    it.country,
                    it.releaseDate,
                    it.previewUrl
                )
            }
        } else {
            resultRequest = response
            return emptyList()
        }
    }
    override fun getRequestStatus() : Response = resultRequest
}
