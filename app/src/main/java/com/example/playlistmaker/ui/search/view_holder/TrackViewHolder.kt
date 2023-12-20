package com.example.playlistmaker.ui.search.view_holder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.ItemTrackListBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.app.utils.ImageWorkPlace
import com.example.playlistmaker.domain.utils.DateFormatter


class TrackViewHolder(
    private val binding: ItemTrackListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) {
        with(binding) {
            tvArtistName.text = track.artistName
            tvTrackName.text = track.trackName
            tvTrackTime.text = DateFormatter.formatMillisToString(track.trackTimeMillis)
            ImageWorkPlace.getRecommendationImage(root.context, artworkUrl100, track.artworkUrl100)
        }
    }
}