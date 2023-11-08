package com.example.playlistmaker.presentation.adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.data.SharedPreferenceConverter
import com.example.playlistmaker.presentation.adapters.view_holder.TrackViewHolder
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.databinding.ItemTrackListBinding
import com.example.playlistmaker.util.ObjectCollection.intentView


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
            write(item)
            holder.bind(item)
            intentView.sendData(context, item)
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
            .putString(newTrack, SharedPreferenceConverter.createJsonFromTrack(track))
            .apply()
    }
}
