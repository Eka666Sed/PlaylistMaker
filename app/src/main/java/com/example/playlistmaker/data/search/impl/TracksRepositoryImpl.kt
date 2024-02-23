package com.example.playlistmaker.data.search.impl


import android.content.SharedPreferences
import com.example.playlistmaker.data.db.FavoriteTrackDao
import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.search.model.ResponseTrack
import com.example.playlistmaker.data.search.model.TracksSearchRequest
import com.example.playlistmaker.data.search.TracksRepository
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.utils.Resource
import com.example.playlistmaker.domain.utils.SharedPreferencesConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val sharedPreferences: SharedPreferences,
    private val sharedPreferencesConverter: SharedPreferencesConverter,
    private val favoriteTrackDao: FavoriteTrackDao
    ) : TracksRepository {
    companion object {
        const val KEY_TRACKS_HISTORY = "key_tracks_history"
        const val MAX_TRACKS_HISTORY_SIZE = 10
    }

    private var trackForPlaying: Track? = null

    override fun searchTracks(text: String): Flow<Resource<List<Track>>> {
        return flow {
            val response = networkClient.doRequest(TracksSearchRequest(text))
            when (response.resultCode) {
                -1 -> emit(Resource.Error("Проверьте подключение к интернету"))
                200 -> {
                    val tracks = (response as ResponseTrack).results.map { it.mapToDomain() }
                    val favoriteTracksIds = favoriteTrackDao.getTracksIds().toSet()
                    emit(
                        Resource.Success(
                            tracks.map { it.copy(isFavorite = it.id in favoriteTracksIds) }
                        )
                    )
                }

                else -> emit(Resource.Error("Ошибка сервера"))
            }
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
