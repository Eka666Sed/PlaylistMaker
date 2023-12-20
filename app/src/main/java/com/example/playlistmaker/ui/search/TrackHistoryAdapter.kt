package com.example.playlistmaker.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.ItemTrackListBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.ui.search.view_holder.TrackViewHolder
import com.example.playlistmaker.presentation.utils.IntentWorkPlace

class TrackHistoryAdapter(
    private val onTrackClicked: (track: Track) -> Unit
) : RecyclerView.Adapter<TrackViewHolder>() {

    private var tracks = emptyList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TrackViewHolder(binding)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = tracks[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { onTrackClicked(item) }
    }

    fun update(list: List<Track>) {
        tracks = list
        notifyDataSetChanged()
    }
}
