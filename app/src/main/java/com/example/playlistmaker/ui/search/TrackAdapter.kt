package com.example.playlistmaker.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.ItemTrackListBinding
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.ui.search.view_holder.TrackViewHolder


class TrackAdapter(
    private val onTrackClicked: (track: Track) -> Unit,
    private val onTrackLongClicked: ((trackId: Long) -> Unit)? = null
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
        holder.itemView.apply {
            setOnClickListener { onTrackClicked(item) }
            setOnLongClickListener {
                onTrackLongClicked?.let { onLongClick -> onLongClick(item.id) }
                true
            }
        }
    }

    fun updateData(newListTrack: List<Track>) {
        tracks = newListTrack
        notifyDataSetChanged()
    }
}
