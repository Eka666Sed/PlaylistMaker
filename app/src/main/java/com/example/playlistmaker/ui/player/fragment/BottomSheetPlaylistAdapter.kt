package com.example.playlistmaker.ui.player.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ItemPlaylistBottomSheetBinding
import com.example.playlistmaker.domain.model.Playlist
import com.example.playlistmaker.ui.util.load

class BottomSheetPlaylistAdapter(
    private val onPlaylistClicked: (Playlist) -> Unit
) : ListAdapter<Playlist, BottomSheetPlaylistAdapter.PlaylistViewHolder>(PlaylistDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaylistViewHolder(ItemPlaylistBottomSheetBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PlaylistViewHolder(
        private val binding: ItemPlaylistBottomSheetBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(playlist: Playlist) {
            with(binding) {
                root.setOnClickListener { onPlaylistClicked(playlist) }
                playlist.coverUri?.let { ivCover.load(it) }
                textName.text = playlist.name
                textTracksCount.text = getTracksCountText(playlist.tracksCount)
            }
        }

        private fun getTracksCountText(tracksCount: Int): String {
            val pluralsString = binding.root.resources.getQuantityString(
                R.plurals.track,
                tracksCount,
                tracksCount
            )
            return "$tracksCount $pluralsString"
        }
    }

    private class PlaylistDiffCallback : DiffUtil.ItemCallback<Playlist>() {

        override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist) =
            oldItem == newItem
    }
}