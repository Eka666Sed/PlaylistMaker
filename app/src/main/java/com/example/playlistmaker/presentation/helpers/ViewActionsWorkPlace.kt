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
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.presentation.DebounceWorkPlace
import com.example.playlistmaker.presentation.adapters.TrackHistoryAdapter
import com.example.playlistmaker.util.ObjectCollection.useCase

object ViewActionsWorkPlace {
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
        trackAdapter: TrackHistoryAdapter
    ) {
        binding.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                showButtonClear(true, binding, context, trackAdapter)
                binding.btnClearHistory.visibility = View.INVISIBLE
                binding.tvHint.visibility = View.GONE
                ActionsAdapterWorkPlace.clearAdapter(binding, context)
                useCase.getWebRequest(binding,context)
            }
            return@setOnEditorActionListener false
        }
    }


    fun textWatcher(
        binding: ActivitySearchBinding,
        context: Context,
        trackAdapter: TrackHistoryAdapter,
        list: MutableList<Track>
    ) {
        val searchRunnable = Runnable { useCase.getWebRequest(binding, context) }

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
            useCase.showHistoryRequest(trackAdapter, list)
            binding.progressBar.visibility = View.GONE
        }
    }

    fun updateData(binding: ActivitySearchBinding, context: Context) {
        binding.btnMessage.setOnClickListener { useCase.getWebRequest(binding,context) }
    }
}