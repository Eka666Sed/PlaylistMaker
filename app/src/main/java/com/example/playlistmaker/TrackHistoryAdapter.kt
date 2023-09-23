package com.example.playlistmaker

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class TrackHistoryAdapter(
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
        return if(listTrack.size >= 10) 10
        else listTrack.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = listTrack[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { notifyItemMoved(position,0) }
    }

    fun clearListAdapter() {
        listTrack = emptyList()
        notifyDataSetChanged()
    }

    fun readPref() : List<Track> {
        val json = pref.getStringSet(key, HashSet())?.toMutableSet() ?: HashSet()
        Log.d("TrackAdapter",json.toString())
        val trackSet = json.map { json ->
            Gson().fromJson(json,Track::class.java)
        }
        Log.d("TrackAdapter",trackSet.toString())
        listTrack = trackSet
        return listTrack
    }
}
