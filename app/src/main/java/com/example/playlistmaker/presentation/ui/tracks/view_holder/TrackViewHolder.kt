package com.example.playlistmaker.presentation.ui.tracks.view_holder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.ItemTrackListBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.utils.DataFormatter
import com.example.playlistmaker.presentation.utils.ImageWorkPlace


class TrackViewHolder(
    private val context: Context,
    private val binding: ItemTrackListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) {
        binding.tvArtistName.text = track.artistName
        binding.tvTrackName.text = track.trackName
        binding.tvTrackTime.text = DataFormatter.getFormattedTrackTime(track)
        ImageWorkPlace.getRecomendationImage(context, false, binding.artworkUrl100, track)
    }
}