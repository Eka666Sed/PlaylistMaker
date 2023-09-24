package com.example.playlistmaker

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackHistoryAdapter(private val context: Context) : RecyclerView.Adapter<TrackViewHolder>() {

    var list = mutableListOf<Track>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track_list, parent, false)
        return TrackViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return if (list.size >= 10) 10
        else list.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
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
