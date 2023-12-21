package com.example.playlistmaker.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.ui.player.PlayerActivity
import com.example.playlistmaker.ui.search.view_model.SearchViewModel

class SearchActivity : AppCompatActivity() {
    private var binding: ActivitySearchBinding? = null
    private lateinit var viewModel: SearchViewModel
    private lateinit var trackHistoryAdapter: TrackHistoryAdapter
    private lateinit var trackAdapter: TrackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        viewModel = ViewModelProvider(this).get<SearchViewModel>()

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding?.apply {
            toolbarSettings.setNavigationOnClickListener { onBackPressed() }
            btnMessage.setOnClickListener { viewModel.onMessageButtonClicked() }
            initTracksRecyclerView()
            initTracksHistoryRecyclerView()
            clearButton.setOnClickListener {
                viewModel.onClearButtonClicked()
            }
        }
        initEditTextSearch()
        initClearButton()
    }

    private fun initTracksRecyclerView() {
        trackAdapter = TrackAdapter(viewModel::onTrackClicked)
        binding?.trackRecyclerView?.adapter = trackAdapter
    }

    private fun initTracksHistoryRecyclerView() {
        trackHistoryAdapter = TrackHistoryAdapter(viewModel::onTrackClicked)
        binding?.trackHistoryRecyclerView?.adapter = trackHistoryAdapter
    }

    private fun initEditTextSearch() {
        binding?.editTextSearch?.apply {
            doOnTextChanged { text, _, _, _ ->
                viewModel.onSearchRequestChanged(text?.toString()?.trim() ?: "")
            }
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewModel.onEnterClicked()
                }
                return@setOnEditorActionListener false
            }
            setOnFocusChangeListener { _, hasFocus -> viewModel.onSearchFocusChanged(hasFocus) }
        }
    }

    private fun initClearButton() {
        binding?.btnClearHistory?.setOnClickListener {
            viewModel.onClearHistoryButtonClicked()
        }
    }

    private fun initObservers() {
        viewModel.state.observe(this) {
            binding?.apply {
                clearButton.isVisible = it.clearButtonVisible
                trackAdapter.updateData(it.tracks)
                trackRecyclerView.isVisible = it.tracks.isNotEmpty()
                trackHistoryAdapter.update(it.tracksHistory)
                layoutHistory.isVisible = it.tracksHistoryVisible
                progressBar.isVisible = it.progressVisible
                btnMessage.isVisible = it.noWebVisible

                val messageResId = if (it.noTracksVisible) R.string.no_content else R.string.no_web
                tvMessage.text = getString(messageResId)

                val messageImageResId =
                    if (it.noTracksVisible) R.drawable.no_content else R.drawable.no_web

                ivMessage.setImageResource(messageImageResId)

                layoutMessage.isVisible = it.messageVisible
            }
        }

        viewModel.event.observe(this) {
            when (it) {
                is SearchScreenEvent.OpenPlayerScreen -> {
                    startActivity(Intent(this, PlayerActivity()::class.java))
                }
                is SearchScreenEvent.ClearSearch->binding?.editTextSearch?.text?.clear()

                else -> hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding?.clearButton?.windowToken, 0)
    }
}