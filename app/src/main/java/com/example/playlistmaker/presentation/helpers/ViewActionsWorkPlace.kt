package com.example.playlistmaker.presentation.helpers

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.R
import com.example.playlistmaker.data.SharedPreferencesDataSource
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.domain.GetSharedPreferenceActionUseCase
import com.example.playlistmaker.domain.GetWebDataUseCase
import com.example.playlistmaker.presentation.DebounceWorkPlace
import com.example.playlistmaker.presentation.adapters.TrackAdapter
import com.example.playlistmaker.presentation.adapters.TrackHistoryAdapter

class ViewActionsWorkPlace(
    private val actionAdapter:ActionsAdapterWorkPlace,
    private val useCaseWeb: GetWebDataUseCase
) {
    fun hideKeyboard(context: Context, binding: ActivitySearchBinding) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.clearButton.windowToken, 0)
    }

    fun hidePicture(binding: ActivitySearchBinding) {
        binding.apply {
            ivMessage.visibility = View.INVISIBLE
            tvMessage.visibility = View.INVISIBLE
        }
    }

    fun showButtonClear(
        show: Boolean,
        binding: ActivitySearchBinding,
        context: Context,
        trackAdapter: TrackHistoryAdapter
    ) {
        if (show) {
            binding.apply {
                btnClearHistory.visibility = View.VISIBLE
                tvHint.visibility = View.VISIBLE
                trackRecyclerView.visibility = View.VISIBLE
                trackRecyclerView.layoutManager = LinearLayoutManager(context)
                trackRecyclerView.adapter = trackAdapter
            }
        } else {
            binding.apply {
                btnClearHistory.visibility = View.INVISIBLE
                tvHint.visibility = View.GONE
                trackRecyclerView.visibility = View.INVISIBLE
                trackRecyclerView.layoutManager = LinearLayoutManager(context)
                trackRecyclerView.adapter = trackAdapter
            }
            trackAdapter.clearListAdapter()
        }
    }

    fun searchButton(
        binding: ActivitySearchBinding,
        context: Context,
        trackAdapter: TrackHistoryAdapter,
        query:String
    ) {
        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                showButtonClear(true, binding, context, trackAdapter)
                binding.btnClearHistory.visibility = View.INVISIBLE
                binding.tvHint.visibility = View.GONE
                actionAdapter.clearAdapter(binding, context)
                /*useCaseWeb.getWebRequest(query)*/
                actionWeb(query, binding, context)
            }
            return@setOnEditorActionListener false
        }
    }


    fun textWatcher(
        binding: ActivitySearchBinding,
        context: Context,
        trackAdapter: TrackHistoryAdapter,
        list: MutableList<Track>,
        query:String
    ) {
        val searchRunnable = Runnable { /*useCaseWeb.getWebRequest(query)*/ actionWeb(query,binding,context)}

        val myTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.clearButton.visibility = View.INVISIBLE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.clearButton.visibility = View.INVISIBLE
                } else {
                    binding.clearButton.visibility = View.VISIBLE
                    binding.progressBar.isVisible
                    showButtonClear(false, binding, context, trackAdapter)
                    DebounceWorkPlace.searchDebounce(searchRunnable)
                    DebounceWorkPlace.clickDebounce()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.editTextSearch.doOnTextChanged { text, _, _, _ ->
            myTextWatcher.onTextChanged(text, 0, 0, 0)
            binding.progressBar.visibility = View.VISIBLE
        }

        binding.clearButton.setOnClickListener {
            binding.editTextSearch.text?.clear()
            hideKeyboard(context, binding)
            hidePicture(binding)
            showButtonClear(true, binding, context, trackAdapter)
            useCaseWeb.showHistoryRequest(trackAdapter, list)
            binding.progressBar.visibility = View.GONE
        }
    }

    fun updateData(binding: ActivitySearchBinding, context: Context, query:String) {
        binding.btnMessage.setOnClickListener {
            /*useCaseWeb.getWebRequest(query)*/
            actionWeb(query, binding, context)
        }
    }

    private fun actionWeb(
        query:String,
        binding: ActivitySearchBinding,
        context: Context,
    ){
        val useCase = GetSharedPreferenceActionUseCase(SharedPreferencesDataSource(context))
        val response = useCaseWeb.getWebRequest(query)
        if(response != null){
            val trackList = response.body()?.results ?: emptyList()

            if (response.isSuccessful && trackList.isNotEmpty()) {
                binding.apply {
                    trackRecyclerView.visibility = View.VISIBLE
                    ivMessage.visibility = View.INVISIBLE
                    btnMessage.visibility = View.INVISIBLE
                    tvMessage.text = ""
                    binding.editTextSearch.visibility = View.VISIBLE
                }

                val trackAdapter = TrackAdapter(context, useCase.getSharedPreferences())
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
            binding.progressBar.visibility = View.GONE
        }else{
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
            binding.progressBar.visibility = View.GONE
        }
    }
}