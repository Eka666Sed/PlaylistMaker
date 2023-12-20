package com.example.playlistmaker.data.search.impl


import android.content.SharedPreferences
import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.search.model.ResponseTrack
import com.example.playlistmaker.data.search.model.TracksSearchRequest
import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.utils.Resource
import com.example.playlistmaker.domain.utils.SharedPreferenceConverter

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val sharedPreferences: SharedPreferences,
    private val sharedPreferencesConverter: SharedPreferenceConverter
    ) : TracksRepository {
    companion object {
        const val KEY_TRACKS_HISTORY = "key_tracks_history"
        const val MAX_TRACKS_HISTORY_SIZE = 10
    }

    private var trackForPlaying: Track? = null

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

    override fun getTracksHistory(): List<Track> {
        return sharedPreferences.getString(KEY_TRACKS_HISTORY, null)?.let {
            sharedPreferencesConverter.convertJsonToList(it)
        } ?: emptyList()
    }

    override fun addTrackToHistory(track: Track) {
        val tracks: MutableList<Track> =
            sharedPreferences.getString(KEY_TRACKS_HISTORY, null)?.let {
                sharedPreferencesConverter.convertJsonToList(it).toMutableList()
            } ?: mutableListOf()

        if (track in tracks.toSet()) {
            tracks.remove(track)
        }

        if (tracks.size >= MAX_TRACKS_HISTORY_SIZE) {
            tracks.removeLast()
        }
        tracks.add(0, track)
        val tracksString = sharedPreferencesConverter.convertListToJson(tracks)
        sharedPreferences.edit()
            .putString(KEY_TRACKS_HISTORY, tracksString)
            .apply()
    }

    override fun clearTrackHistory() {
        sharedPreferences.edit().putString(KEY_TRACKS_HISTORY, null).apply()
    }

    override fun getTrackForPlaying(): Track? {
        return trackForPlaying?.let {
            val copy = trackForPlaying?.copy()
            trackForPlaying = null
            copy
        }
    }

    override fun saveTrackForPlaying(track: Track?) {
        trackForPlaying = track
    }
}
