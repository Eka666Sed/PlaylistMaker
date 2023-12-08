package com.example.playlistmaker.domain.search

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.data.search.DebounceWorkPlace
import com.example.playlistmaker.databinding.ItemTrackListBinding
import com.example.playlistmaker.creator.ConverterCreator
import com.example.playlistmaker.data.player.Track
import com.example.playlistmaker.domain.sharing.model.IntentWorkPlace


class TrackAdapter(
    private val context: Context,
    private val pref: SharedPreferences
) :
    RecyclerView.Adapter<TrackViewHolder>() {

    private var listTrack: List<Track> = emptyList()
    private val newTrack = "new_track"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return TrackViewHolder(context, binding)
    }

    override fun getItemCount(): Int {
        return listTrack.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = listTrack[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            DebounceWorkPlace.clickDebounce()
            write(item)
            holder.bind(item)
            IntentWorkPlace.sendData(context, item)
        }
    }

    fun updateData(newListTrack: List<Track>) {
        listTrack = newListTrack
        notifyDataSetChanged()
    }

    fun clearListAdapter() {
        listTrack = emptyList()
        notifyDataSetChanged()
    }

    private fun write(track: Track) {
        pref.edit()
            .putString(newTrack, ConverterCreator.sharedPreferenceConverter().createJsonFromTrack(track))
            .apply()
    }
}
