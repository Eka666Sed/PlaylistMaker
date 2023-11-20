package com.example.playlistmaker.presentation.ui.tracks

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.app.utils.DebounceWorkPlace
import com.example.playlistmaker.databinding.ItemTrackListBinding
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.presentation.ui.tracks.view_holder.TrackViewHolder
import com.example.playlistmaker.presentation.utils.IntentWorkPlace

class TrackHistoryAdapter(private val context: Context) : RecyclerView.Adapter<TrackViewHolder>() {

    private var list = mutableListOf<Track>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val binding = ItemTrackListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return TrackViewHolder(context, binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            DebounceWorkPlace.clickDebounce()
            val intent = IntentWorkPlace
            intent.sendData(context,item)
        }
    }

    fun clearListAdapter() {
        list.clear()
        notifyDataSetChanged()
    }

    fun update(list: List<Track>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }
}
