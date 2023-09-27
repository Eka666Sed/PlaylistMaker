package com.example.playlistmaker

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlistmaker.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val NEW_TRACK = "new_track"
private const val MAIN_KEY = "main_key"
private const val PREF = "pref_data"

class SearchActivity : AppCompatActivity() {
    private var savedText: String = ""
    private var binding: ActivitySearchBinding? = null
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    private var listTrack = mutableListOf<Track>()
    private lateinit var prefData: SharedPreferences
    private val trackAdapter = TrackHistoryAdapter(this@SearchActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.clearButton?.visibility = View.INVISIBLE
        binding?.toolbarSettings?.setNavigationOnClickListener { onBackPressed() }
        prefData = getSharedPreferences(PREF, MODE_PRIVATE)
        showButtonClear(false)
        binding?.editTextSearch?.requestFocus()
        if (savedInstanceState != null) {
            savedText = savedInstanceState.getString((getString(R.string.search_text)), "")
            binding?.editTextSearch?.setText(savedText)
        }
    }

    override fun onStart() {
        super.onStart()
        getDataTrack()
        showAdapterHistory()
        getDataForTrack()
        prefData.registerOnSharedPreferenceChangeListener(listener)
        showHistoryRequest()
        searchButton()
        updateData()
        clearSharedPreference()
        textWatcher()
    }

    override fun onStop() {
        super.onStop()
        val pref = SharedPreferenceWorkPlace
        pref.saveDataTrack(prefData,listTrack, MAIN_KEY)
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
        val sharedPreferencesConverter = SharedPreferenceConverter
        val tracks = prefData.getString(MAIN_KEY, null)
        if (tracks != null) {
            listTrack = sharedPreferencesConverter.createTracksListFromJson(tracks).toMutableList()
        }
    }

    private fun showAdapterHistory() {
        val trackAdapter = trackAdapter
        trackAdapter.update(listTrack)
    }

    private fun textWatcher() {
        val myTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding?.clearButton?.visibility = View.INVISIBLE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding?.clearButton?.visibility = View.INVISIBLE
                } else {
                    binding?.clearButton?.visibility = View.VISIBLE
                    showButtonClear(false)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        binding?.editTextSearch?.doOnTextChanged { text, _, _, _ ->
            myTextWatcher.onTextChanged(text, 0, 0, 0)
        }

        binding?.editTextSearch?.setOnClickListener {
            if (trackAdapter.itemCount>0) {
                showButtonClear(true)
                showHistoryRequest()
            }
        }

        binding?.clearButton?.setOnClickListener {
            binding?.editTextSearch?.text?.clear()
            hideKeyboard()
            hidePicture()
            showButtonClear(true)
            showHistoryRequest()
        }
    }

    private fun hidePicture() {
        binding?.ivMessage?.visibility = View.INVISIBLE
        binding?.tvMessage?.visibility = View.INVISIBLE
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding?.clearButton?.windowToken, 0)
    }

    private fun showButtonClear(show: Boolean) {
        if (show) {
            binding?.btnClearHistory?.visibility = View.VISIBLE
            binding?.tvHint?.visibility = View.VISIBLE
            binding?.trackRecyclerView?.visibility = View.VISIBLE
            binding?.trackRecyclerView?.layoutManager = LinearLayoutManager(this@SearchActivity)
            binding?.trackRecyclerView?.adapter = trackAdapter
        } else {
            binding?.btnClearHistory?.visibility = View.INVISIBLE
            binding?.tvHint?.visibility = View.GONE
            binding?.trackRecyclerView?.visibility = View.INVISIBLE
            binding?.trackRecyclerView?.layoutManager = LinearLayoutManager(this@SearchActivity)
            trackAdapter.clearListAdapter()
            binding?.trackRecyclerView?.adapter = trackAdapter
        }
    }

    private fun clearAdapter() {
        val clearAdapter = TrackAdapter(this, prefData)
        clearAdapter.clearListAdapter()
        binding?.trackRecyclerView?.adapter = clearAdapter
    }

    private fun updateData() {
        binding?.btnMessage?.setOnClickListener { getWebRequest() }
    }

    private fun searchButton() {
        binding?.editTextSearch?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                showButtonClear(true)
                binding?.btnClearHistory?.visibility = View.INVISIBLE
                binding?.tvHint?.visibility = View.GONE
                clearAdapter()
                getWebRequest()
            }
            return@setOnEditorActionListener false
        }
    }

    private fun getWebRequest() {
        val query = binding?.editTextSearch?.text.toString().trim()
        val apiService = TrackApiService.create
        apiService.search(query).enqueue(object : Callback<ResponseTrack> {
            override fun onResponse(
                call: Call<ResponseTrack>,
                response: Response<ResponseTrack>
            ) {
                handleResponse(response)
            }

            override fun onFailure(call: Call<ResponseTrack>, t: Throwable) {
                handleFailure()
            }
        })
    }

    private fun handleResponse(response: Response<ResponseTrack>) {
        val trackList = response.body()?.results ?: emptyList()

        if (response.isSuccessful && trackList.isNotEmpty()) {
            binding?.trackRecyclerView?.visibility = View.VISIBLE
            binding?.ivMessage?.visibility = View.INVISIBLE
            binding?.btnMessage?.visibility = View.INVISIBLE
            binding?.tvMessage?.text = ""

            val trackAdapter =
                TrackAdapter(this@SearchActivity, prefData)
            binding?.trackRecyclerView?.layoutManager = LinearLayoutManager(this@SearchActivity)
            binding?.trackRecyclerView?.adapter = trackAdapter
            trackAdapter.updateData(trackList)

        } else {
            binding?.trackRecyclerView?.visibility = View.INVISIBLE
            binding?.ivMessage?.visibility = View.VISIBLE
            binding?.tvMessage?.visibility = View.VISIBLE
            binding?.btnMessage?.visibility = View.INVISIBLE

            if (response.isSuccessful) {
                binding?.tvMessage?.text = getString(R.string.no_content)
                binding?.ivMessage?.setImageResource(
                    if (isNightModeEnabled()) R.drawable.dark_no_content
                    else R.drawable.no_content
                )
            } else {
                binding?.tvMessage?.text = getString(R.string.no_web)
                binding?.ivMessage?.setImageResource(
                    if (isNightModeEnabled()) R.drawable.dark_no_content
                    else R.drawable.no_content
                )
            }
        }
    }

    private fun handleFailure() {
        binding?.trackRecyclerView?.visibility = View.INVISIBLE
        binding?.ivMessage?.visibility = View.VISIBLE
        binding?.tvMessage?.visibility = View.VISIBLE
        binding?.btnMessage?.visibility = View.VISIBLE
        binding?.tvMessage?.text = getString(R.string.no_web)
        binding?.ivMessage?.setImageResource(
            if (isNightModeEnabled()) R.drawable.dark_no_web
            else R.drawable.no_web
        )
    }

    private fun isNightModeEnabled(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun clearSharedPreference() {
        binding?.btnClearHistory?.setOnClickListener {
            prefData.edit().clear().apply()
            trackAdapter.clearListAdapter()
            listTrack.clear()
            showButtonClear(false)
        }
    }

    private fun showHistoryRequest() {
        trackAdapter.update(listTrack)
    }

    private fun getDataForTrack() {
        val sharedPreferenceConverter = SharedPreferenceConverter
        fun addTrack(track: String){
            val createTrack = sharedPreferenceConverter.createTrackFromJson(track)
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
                }else{
                    listTrack.removeLast()
                    addTrack(track!!)
                }
            }
        }
    }
}
