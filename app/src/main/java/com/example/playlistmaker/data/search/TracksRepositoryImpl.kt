package com.example.playlistmaker.data.search


import com.example.playlistmaker.data.player.ResponseTrack
import com.example.playlistmaker.domain.search.TracksRepository
import com.example.playlistmaker.data.player.Track
import com.example.playlistmaker.domain.Resource

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {
    override fun searchTracks(text: String): Resource<List<Track>> {
        val response = networkClient.doRequest(TracksSearchRequest(text))
        return when (response.resultCode){
            -1 -> Resource.Error("Проверьте подключение к интернету")
            200 -> {
                Resource.Success((response as ResponseTrack).results.map {
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
                })
            }
            else -> Resource.Error("Ошибка сервера")
        }
    }
}
