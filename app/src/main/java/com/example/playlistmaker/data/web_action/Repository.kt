package com.example.playlistmaker.data.web_action

import com.example.playlistmaker.data.TrackApiService
import com.example.playlistmaker.data.entities.ResponseTrack
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.presentation.adapters.TrackHistoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    var responseApi: Response<ResponseTrack>? = null

    fun getWebRequest(query:String){
        //val query = binding.editTextSearch.text.toString().trim()
        if (query.isNotEmpty()) {
            val apiService = TrackApiService.create
            apiService.search(query).enqueue(object : Callback<ResponseTrack> {
                override fun onResponse(
                    call: Call<ResponseTrack>,
                    response: Response<ResponseTrack>
                ) {
                    /*handleResponse(response*//*,binding,context*//*)*/
                    //binding.progressBar.visibility = View.GONE
                    responseApi = response
                }

                override fun onFailure(call: Call<ResponseTrack>, t: Throwable) {
                    /*handleFailure(*//*binding,context*//*)*/
                    //binding.progressBar.visibility = View.GONE
                }
            })
        }
    }



    fun handleResponse(
        response: Response<ResponseTrack>,
        /*binding: ActivitySearchBinding,
        context: Context*/
    ) : Response<ResponseTrack> {
        val trackList = response.body()?.results ?: emptyList()

        if (response.isSuccessful && trackList.isNotEmpty()) {
            /*binding.apply {
                trackRecyclerView.visibility = View.VISIBLE
                ivMessage.visibility = View.INVISIBLE
                btnMessage.visibility = View.INVISIBLE
                tvMessage.text = ""
                binding.editTextSearch.visibility = View.VISIBLE
            }

            val trackAdapter = TrackAdapter(context, useCase.getSharedPreferences())
            binding.trackRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.trackRecyclerView.adapter = trackAdapter
            trackAdapter.updateData(trackList)*/

        } else {
            /*binding.apply {
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
            }*/
        }
        //binding.progressBar.visibility = View.GONE
        return response
    }


    fun handleFailure(/*binding: ActivitySearchBinding, context: Context*/) {
        /*binding.apply {
            trackRecyclerView.visibility = View.INVISIBLE
            ivMessage.visibility = View.VISIBLE
            tvMessage.visibility = View.VISIBLE
            btnMessage.visibility = View.VISIBLE
            tvMessage.text = context.getString(R.string.no_web)
            ivMessage.setImageResource(
                R.drawable.no_web
            )
        }*/
        //binding.progressBar.visibility = View.GONE
    }

    fun showHistoryRequest(trackAdapter: TrackHistoryAdapter, listTrack: MutableList<Track>) {
        trackAdapter.update(listTrack)
    }
}