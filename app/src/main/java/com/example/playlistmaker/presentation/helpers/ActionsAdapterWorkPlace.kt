package com.example.playlistmaker.presentation.helpers

import android.content.Context
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.presentation.adapters.TrackAdapter
import com.example.playlistmaker.presentation.adapters.TrackHistoryAdapter
import com.example.playlistmaker.util.ObjectCollection.sharedPreferences

object ActionsAdapterWorkPlace {
    fun clearAdapter(binding: ActivitySearchBinding, context: Context) {
        val clearAdapter =
            TrackAdapter(context, sharedPreferences.getSharedPreferences(context))
        clearAdapter.clearListAdapter()
        binding.trackRecyclerView.adapter = clearAdapter
    }

    fun showAdapterHistory(trackAdapter: TrackHistoryAdapter, list: MutableList<Track>) {
        val trackAdapter = trackAdapter
        trackAdapter.update(list)
    }
}