package com.example.playlistmaker.ui.search.view_holder

import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.ItemTrackListBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.utils.DateFormatter
import com.example.playlistmaker.ui.util.load


class TrackViewHolder(
    private val binding: ItemTrackListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) {
        with(binding) {
            tvArtistName.text = track.artistName
            tvTrackName.text = track.trackName
            tvTrackTime.text = DateFormatter.formatMillisToString(track.trackTimeMillis)
            artworkUrl100.load(track.artworkUrl100)
        }
    }
}