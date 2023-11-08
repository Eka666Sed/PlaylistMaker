package com.example.playlistmaker.presentation.adapters.view_holder

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.data.DataSource.setValueForViewHolder
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.databinding.ItemTrackListBinding

class TrackViewHolder(
    private val context: Context,
    private val binding: ItemTrackListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) { setValueForViewHolder(binding, track, context)
    }
}