package com.example.playlistmaker.data.web_action

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.data.SharedPreferenceWorkPlace
import com.example.playlistmaker.data.TrackApiService
import com.example.playlistmaker.data.entities.ResponseTrack
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.presentation.adapters.TrackAdapter
import com.example.playlistmaker.presentation.adapters.TrackHistoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object WebWorkPlace {

    fun getWebRequest(binding: ActivitySearchBinding, context: Context) {
        val query = binding.editTextSearch.text.toString().trim()
        if (query.isNotEmpty()) {
            val apiService = TrackApiService.create
            apiService.search(query).enqueue(object : Callback<ResponseTrack> {
                override fun onResponse(
                    call: Call<ResponseTrack>,
                    response: Response<ResponseTrack>
                ) {
                    handleResponse(response,binding,context)
                    binding.progressBar.visibility = View.GONE
                }

                override fun onFailure(call: Call<ResponseTrack>, t: Throwable) {
                    handleFailure(binding,context)
                    binding.progressBar.visibility = View.GONE
                }
            })
        }
    }


    private fun handleResponse(
        response: Response<ResponseTrack>,
        binding: ActivitySearchBinding,
        context: Context
    ) {
        val trackList = response.body()?.results ?: emptyList()

        if (response.isSuccessful && trackList.isNotEmpty()) {
            binding.apply {
                trackRecyclerView.visibility = View.VISIBLE
                ivMessage.visibility = View.INVISIBLE
                btnMessage.visibility = View.INVISIBLE
                tvMessage.text = ""
                binding.editTextSearch.visibility = View.VISIBLE
            }

            val trackAdapter = TrackAdapter(context, SharedPreferenceWorkPlace.getSharedPreferences(context))
            binding.trackRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.trackRecyclerView.adapter = trackAdapter
            trackAdapter.updateData(trackList)

        } else {
            binding.apply {
                trackRecyclerView.visibility = View.INVISIBLE
                ivMessage.visibility = View.VISIBLE
                tvMessage.visibility = View.VISIBLE
                btnMessage.visibility = View.INVISIBLE
            }

            if (response.isSuccessful) {
                binding.tvMessage.text = context.getString(R.string.no_content)
                binding.ivMessage.setImageResource(
                    R.drawable.no_content
                )
            } else {
                binding.tvMessage.text = context.getString(R.string.no_web)
                binding.ivMessage.setImageResource(
                    R.drawable.no_content
                )
            }
        }
    }


    private fun handleFailure(binding: ActivitySearchBinding, context: Context) {
        binding.apply {
            trackRecyclerView.visibility = View.INVISIBLE
            ivMessage.visibility = View.VISIBLE
            tvMessage.visibility = View.VISIBLE
            btnMessage.visibility = View.VISIBLE
            tvMessage.text = context.getString(R.string.no_web)
            ivMessage.setImageResource(
                R.drawable.no_web
            )
        }
    }

    fun showHistoryRequest(trackAdapter: TrackHistoryAdapter, listTrack: MutableList<Track>) {
        trackAdapter.update(listTrack)
    }
}