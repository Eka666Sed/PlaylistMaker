package com.example.playlistmaker.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.ui.player.PlayerActivity
import com.example.playlistmaker.ui.search.view_model.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private  val searchViewModel: SearchViewModel by viewModel()
    private var trackHistoryAdapter: TrackHistoryAdapter? = null
    private var trackAdapter: TrackAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initViews() {
        binding?.run {
            btnMessage.setOnClickListener { searchViewModel.onMessageButtonClicked() }
            clearButton.setOnClickListener {
                searchViewModel.onClearButtonClicked()
            }
        }
        initTracksRecyclerView()
        initTracksHistoryRecyclerView()
        initEditTextSearch()
        initClearButton()
    }

    private fun initTracksRecyclerView() {
        trackAdapter = TrackAdapter(searchViewModel::onTrackClicked)
        binding?.trackRecyclerView?.adapter = trackAdapter
    }

    private fun initTracksHistoryRecyclerView() {
        trackHistoryAdapter = TrackHistoryAdapter(searchViewModel::onTrackClicked)
        binding?.trackHistoryRecyclerView?.adapter = trackHistoryAdapter
    }

    private fun initEditTextSearch() {
        binding?.editTextSearch?.apply {
            doOnTextChanged { text, _, _, _ ->
                searchViewModel.onSearchRequestChanged(text?.toString()?.trim() ?: "")
            }
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    searchViewModel.onEnterClicked()
                }
                return@setOnEditorActionListener false
            }
            setOnFocusChangeListener { _, hasFocus -> searchViewModel.onSearchFocusChanged(hasFocus) }
        }
    }

    private fun initClearButton() {
        binding?.btnClearHistory?.setOnClickListener {
            searchViewModel.onClearHistoryButtonClicked()
        }
    }

    private fun initObservers() {
        searchViewModel.state.observe(viewLifecycleOwner) {
            binding?.apply {
                clearButton.isVisible = it.clearButtonVisible
                trackAdapter?.updateData(it.tracks)
                trackRecyclerView.isVisible = it.tracks.isNotEmpty()
                trackHistoryAdapter?.update(it.tracksHistory)
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

        searchViewModel.event.observe(viewLifecycleOwner) {
            when (it) {
                is SearchScreenEvent.OpenPlayerScreen -> {
                    startActivity(Intent(requireContext(), PlayerActivity()::class.java))
                }
                is SearchScreenEvent.ClearSearch->binding?.editTextSearch?.text?.clear()

                else -> hideKeyboard()
            }
        }
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding?.clearButton?.windowToken, 0)
    }
}