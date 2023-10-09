package com.example.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TrackHistoryAdapter(private val context: Context) : RecyclerView.Adapter<TrackViewHolder>() {

    private var list = mutableListOf<Track>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_track_list, parent, false)
        return TrackViewHolder(view, context)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val item = list[position]

        val bundle = Bundle()
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val itemJson = SharedPreferenceConverter.createJsonFromTrack(item)
            val intent = Intent(context, MediaActivity::class.java)
            intent.putExtra("key", itemJson)
            context.startActivity(intent)
            Log.d("mes", itemJson)
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
