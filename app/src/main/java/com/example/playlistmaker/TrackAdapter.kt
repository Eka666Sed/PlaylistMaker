package com.example.playlistmaker

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson


class TrackAdapter(
    private val context: Context,
    private val pref: SharedPreferences,
    private val key:String
) :
    RecyclerView.Adapter<TrackViewHolder>() {

    private var listTrack: List<Track> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track_list, parent, false)
        return TrackViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return listTrack.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = listTrack[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {write(item,position)}
    }

    fun updateData(newListTrack: List<Track>) {
        listTrack = newListTrack
        notifyDataSetChanged()
    }

    fun clearListAdapter() {
        listTrack = emptyList()
        notifyDataSetChanged()
    }
    private fun write(track: Track, position: Int) {
        val json = Gson().toJson(track)
        val setTrack = pref.getStringSet(key, HashSet())?.toMutableSet() ?: HashSet()

        if (setTrack.contains(json)) {
            notifyItemMoved(position,0)
        }
        setTrack.add(json)
        pref.edit()
            .putStringSet(key, setTrack)
            .apply()
        if (position != -1) {
            notifyItemChanged(position)
        }
        Log.d("possition",position.toString())
    }
}
