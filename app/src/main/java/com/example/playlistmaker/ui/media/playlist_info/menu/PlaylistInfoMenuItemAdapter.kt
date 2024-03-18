package com.example.playlistmaker.ui.media.playlist_info.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.ItemPlaylistInfoMenuBinding

class PlaylistInfoMenuItemAdapter :
    RecyclerView.Adapter<PlaylistInfoMenuItemAdapter.PlaylistInfoMenuItemViewHolder>() {

    private var items = emptyList<PlaylistInfoMenuItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaylistInfoMenuItemViewHolder {
        val binding = ItemPlaylistInfoMenuBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistInfoMenuItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PlaylistInfoMenuItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateItems(newItems: List<PlaylistInfoMenuItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class PlaylistInfoMenuItemViewHolder(
        private val binding: ItemPlaylistInfoMenuBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaylistInfoMenuItem) {
            with(binding) {
                tvText.text = root.context.getString(item.textResId)
                root.setOnClickListener { item.onClick() }
            }
        }
    }
}