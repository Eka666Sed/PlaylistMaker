package com.example.playlistmaker.presentation.adapters.view_holder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.databinding.ItemTrackListBinding
import com.example.playlistmaker.util.ObjectCollection.formatedData
import com.example.playlistmaker.util.ObjectCollection.image

class TrackViewHolder(
    private val context: Context,
    private val binding: ItemTrackListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) {
        binding.tvArtistName.text = track.artistName
        binding.tvTrackName.text = track.trackName
        binding.tvTrackTime.text = formatedData.getFormattedTrackTime(track)
        image.getRecomendationImage(context, false, binding.artworkUrl100, track)
    }
}