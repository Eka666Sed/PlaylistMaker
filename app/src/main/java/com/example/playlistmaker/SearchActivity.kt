package com.example.playlistmaker

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlistmaker.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val NEW_TRACK = "new_track"
private const val MAIN_KEY = "main_key"


class SearchActivity : AppCompatActivity() {
    private var toolbarSettings: Toolbar? = null
    private var search: EditText? = null
    private var button: ImageButton? = null
    private var rv: RecyclerView? = null
    private var iVMessage: ImageView? = null
    private var tvMessage: TextView? = null
    private var btnMessage: Button? = null
    private var savedText: String = ""
    private var binding: ActivitySearchBinding? = null
    private var btnClearHistory: Button? = null
    private var textHint:TextView? = null
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    private var listTrack = mutableListOf<Track>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        search = binding?.editTextSearch
        button = binding?.clearButton
        rv = binding?.trackRecyclerView
        iVMessage = binding?.ivMessage
        tvMessage = binding?.tvMessage
        btnMessage = binding?.btnMessage
        toolbarSettings = binding?.toolbarSettings
        btnClearHistory = binding?.btnClearHistory
        textHint = binding?.tvHint
        button?.visibility = View.INVISIBLE
        toolbarSettings?.setNavigationOnClickListener { onBackPressed() }
        getDataTrack()
        showAdapterHistory()
        getDataForTrack()
        showHistoryRequest()
        searchButton()
        updateData()
        clearSharedPreference()
        if (savedInstanceState != null) {
            savedText = savedInstanceState.getString((getString(R.string.search_text)), "")
            search?.setText(savedText)
        }
        textWatcher()
        getActionEditText()
    }

    override fun onStop() {
        super.onStop()
        saveDataTrack()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedText = search?.text.toString()
        outState.putString((getString(R.string.search_text)), savedText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedText = savedInstanceState.getString((getString(R.string.search_text)), "")
        search?.setText(savedText)
    }

    private fun saveDataTrack(){
        val sharedPreferencesConverter = SharedPreferenceConverter
        createSharedPreference().edit()
            .putString(MAIN_KEY,sharedPreferencesConverter.createJsonFromTracksList(listTrack))
            .apply()
    }

    private fun getDataTrack(){
        val sharedPreferencesConverter = SharedPreferenceConverter
        val tracks = createSharedPreference().getString(MAIN_KEY,null)
        if(tracks != null){
            listTrack = sharedPreferencesConverter.createTracksListFromJson(tracks).toMutableList()
        }
    }

    private fun showAdapterHistory(){
        val trackAdapter = dataForTrackHistoryAdapter()
        trackAdapter.update(listTrack)
    }


    private fun textWatcher() {
        val myTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                button?.visibility = View.INVISIBLE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    button?.visibility = View.INVISIBLE
                } else {
                    button?.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        search?.doOnTextChanged { text, _, _, _ ->
            myTextWatcher.onTextChanged(text, 0, 0, 0)
        }
        button?.setOnClickListener {
            search?.text?.clear()
            hideKeyboard()
            hidePicture()
            clearAdapter()
            showButtonClear(true)
            showHistoryRequest()
        }
    }

    private fun hidePicture() {
        iVMessage?.visibility = View.INVISIBLE
        tvMessage?.visibility = View.INVISIBLE
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(button?.windowToken, 0)
        showButtonClear(true)
    }

    private fun showButtonClear(show: Boolean) {
        if (show) {
            btnClearHistory?.visibility = View.VISIBLE
            textHint?.visibility = View.VISIBLE
        }
        else {
            btnClearHistory?.visibility = View.INVISIBLE
            textHint?.visibility = View.GONE
        }
    }

    private fun clearAdapter() {
        val clearAdapter = TrackAdapter(this, createSharedPreference())
        clearAdapter.clearListAdapter()
        rv?.adapter = clearAdapter
    }

    private fun updateData() {
        btnMessage?.setOnClickListener { getWebRequest() }
    }

    private fun searchButton() {
        search?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                showButtonClear(false)
                clearAdapter()
                getWebRequest()
            }
            return@setOnEditorActionListener false
        }
    }

    private fun getWebRequest() {
        val query = search?.text.toString().trim()
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
            rv?.visibility = View.VISIBLE
            iVMessage?.visibility = View.INVISIBLE
            btnMessage?.visibility = View.INVISIBLE
            tvMessage?.text = ""

            val trackAdapter =
                TrackAdapter(this@SearchActivity, createSharedPreference())
            rv?.layoutManager = LinearLayoutManager(this@SearchActivity)
            rv?.adapter = trackAdapter
            trackAdapter.updateData(trackList)

        } else {
            rv?.visibility = View.INVISIBLE
            iVMessage?.visibility = View.VISIBLE
            tvMessage?.visibility = View.VISIBLE
            btnMessage?.visibility = View.INVISIBLE

            if (response.isSuccessful) {
                tvMessage?.text = getString(R.string.no_content)
                iVMessage?.setImageResource(
                    if (isNightModeEnabled()) R.drawable.dark_no_content
                    else R.drawable.no_content
                )
            } else {
                tvMessage?.text = getString(R.string.no_web)
                iVMessage?.setImageResource(
                    if (isNightModeEnabled()) R.drawable.dark_no_content
                    else R.drawable.no_content
                )
            }
        }
    }

    private fun handleFailure() {
        rv?.visibility = View.INVISIBLE
        iVMessage?.visibility = View.VISIBLE
        tvMessage?.visibility = View.VISIBLE
        btnMessage?.visibility = View.VISIBLE
        tvMessage?.text = getString(R.string.no_web)
        iVMessage?.setImageResource(
            if (isNightModeEnabled()) R.drawable.dark_no_web
            else R.drawable.no_web
        )
    }

    private fun isNightModeEnabled(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun showHistoryRequest() {
        val trackAdapter = dataForTrackHistoryAdapter()
        createSharedPreference().registerOnSharedPreferenceChangeListener(listener)
        trackAdapter.update(listTrack)
        if (trackAdapter.list.isEmpty()) showButtonClear(false)
        else showButtonClear(true)
        rv?.visibility = View.VISIBLE
        rv?.adapter = trackAdapter
    }

    private fun createSharedPreference(): SharedPreferences {
        val name = getString(R.string.prefData)
        return getSharedPreferences(name, MODE_PRIVATE)
    }

    private fun clearSharedPreference() {
        btnClearHistory?.setOnClickListener {
            createSharedPreference().edit().clear().apply()
            dataForTrackHistoryAdapter().clearListAdapter()
            listTrack.clear()
            showButtonClear(false)
        }
    }

    private fun dataForTrackHistoryAdapter(): TrackHistoryAdapter {
        val trackAdapter =
            TrackHistoryAdapter(this@SearchActivity)
        rv?.layoutManager = LinearLayoutManager(this@SearchActivity)
        rv?.adapter = trackAdapter
        return trackAdapter
    }

    private fun getActionEditText() {
        search?.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) showButtonClear(false)
            else showButtonClear(true)
        }
    }

    private fun getDataForTrack() {
        val trackAdapter = TrackHistoryAdapter(this@SearchActivity)
        val sharedPreferenceConverter = SharedPreferenceConverter
        listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == NEW_TRACK) {
                val track = sharedPreferences?.getString(NEW_TRACK, null)
                if (track != null) {
                    listTrack.add(0,sharedPreferenceConverter.createTrackFromJson(track))
                    trackAdapter.notifyItemInserted(0)
                    Log.d("listTrack",trackAdapter.list.toString())
                }
            }
        }
    }
}