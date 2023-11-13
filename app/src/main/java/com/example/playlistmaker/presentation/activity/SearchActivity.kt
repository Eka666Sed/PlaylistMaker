package com.example.playlistmaker.presentation.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.presentation.adapters.TrackHistoryAdapter
import com.example.playlistmaker.data.SharedPreferenceConverter
import com.example.playlistmaker.data.SharedPreferencesDataSource
import com.example.playlistmaker.data.entities.Track
import com.example.playlistmaker.data.web_action.Repository
import com.example.playlistmaker.databinding.ActivitySearchBinding
import com.example.playlistmaker.domain.GetSharedPreferenceActionUseCase
import com.example.playlistmaker.domain.GetWebDataUseCase
import com.example.playlistmaker.presentation.helpers.ActionsAdapterWorkPlace
import com.example.playlistmaker.presentation.helpers.ViewActionsWorkPlace

class SearchActivity : AppCompatActivity() {
    private var savedText: String = ""
    private var binding: ActivitySearchBinding? = null
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    private var listTrack = mutableListOf<Track>()
    private lateinit var prefData: SharedPreferences
    private val trackAdapter = TrackHistoryAdapter(this@SearchActivity)
    private val useCase = GetSharedPreferenceActionUseCase(SharedPreferencesDataSource(this@SearchActivity))
    private val useCaseWeb = GetWebDataUseCase(Repository(/*this@SearchActivity*/))
    private val adapterAction = ActionsAdapterWorkPlace(useCase)
    private val useCaseView = ViewActionsWorkPlace(adapterAction,useCaseWeb)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.editTextSearch?.onFocusChangeListener
        binding?.clearButton?.visibility = View.INVISIBLE
        binding?.toolbarSettings?.setNavigationOnClickListener { onBackPressed() }
        prefData = getSharedPreferences(PREF, MODE_PRIVATE)
        if (savedInstanceState != null) {
            savedText = savedInstanceState.getString((getString(R.string.search_text)), "")
            binding?.editTextSearch?.setText(savedText)
        }
        binding?.editTextSearch?.setOnFocusChangeListener { _, hasFocus ->
            if ((hasFocus) && (trackAdapter.itemCount > 0)) {
                useCaseView.showButtonClear(true,binding!!,this,trackAdapter)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getDataTrack()
        adapterAction.showAdapterHistory(trackAdapter,listTrack)
        getDataForTrack()
        prefData.registerOnSharedPreferenceChangeListener(listener)
        useCaseWeb.showHistoryRequest(trackAdapter,listTrack)
        useCaseView.searchButton(binding!!,this,trackAdapter,binding!!.editTextSearch.text.toString().trim())
        useCaseView.updateData(binding!!,this,binding!!.editTextSearch.text.toString().trim())
        clearSharedPreference()
        useCaseView.textWatcher(binding!!,this,trackAdapter,listTrack,binding!!.editTextSearch.text.toString().trim())
    }

    fun clearSharedPreference(){
        //clearSharedPreference(this)
        useCase.clearSharedPreference()
        binding?.btnClearHistory?.setOnClickListener {
            //SharedPreferencesDataSource.getSharedPreferences(this).edit().clear().apply()
            useCase.clearSharedPreference()
            trackAdapter.clearListAdapter()
            listTrack.clear()
            useCaseView.showButtonClear(false, binding!!, this, trackAdapter)
        }
    }

    override fun onStop() {
        super.onStop()
        SharedPreferencesDataSource(this).saveDataTrack(prefData, listTrack, MAIN_KEY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedText = binding?.editTextSearch?.text.toString()
        outState.putString((getString(R.string.search_text)), savedText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedText = savedInstanceState.getString((getString(R.string.search_text)), "")
        binding?.editTextSearch?.setText(savedText)
    }

    private fun getDataTrack() {
        val tracks = prefData.getString(MAIN_KEY, null)
        if (tracks != null) {
            listTrack = SharedPreferenceConverter.createTracksListFromJson(tracks).toMutableList()
        }
    }

    private fun getDataForTrack() {
        fun addTrack(track: String) {
            val createTrack = SharedPreferenceConverter.createTrackFromJson(track)
            if (listTrack.contains(createTrack)) {
                listTrack.remove(createTrack)
            }
            listTrack.add(0, createTrack)
            trackAdapter.notifyItemInserted(0)
        }
        listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == NEW_TRACK) {
                val track = sharedPreferences?.getString(NEW_TRACK, null)
                if (listTrack.size < 10) {
                    if (track != null) {
                        addTrack(track)
                    }
                } else {
                    listTrack.removeLast()
                    addTrack(track!!)
                }
            }
        }
    }
    companion object{
        private const val NEW_TRACK = "new_track"
        private const val MAIN_KEY = "main_key"
        private const val PREF = "pref_data"
    }
}