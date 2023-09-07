package com.example.playlistmaker

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private lateinit var toolbarSettings: Toolbar
    private lateinit var search: EditText
    private lateinit var button: ImageButton
    private lateinit var rv: RecyclerView
    private lateinit var iVMessage: ImageView
    private lateinit var tvMessage: TextView
    private lateinit var btnMessage: Button
    private var savedText: String = ""

    companion object {
        const val search_text = "search_text"
        const val noWeb = "Проблемы со связью \n\nЗагрузка не удалась. " +
                "Проверьте подключение к интернету"
        const val noContent = "Ничего не нашлось"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        search = findViewById(R.id.editTextSearch)
        button = findViewById(R.id.clear_button)
        rv = findViewById(R.id.track_recycler_view)
        iVMessage = findViewById(R.id.iv_message)
        tvMessage = findViewById(R.id.tv_message)
        btnMessage = findViewById(R.id.btn_message)
        toolbarSettings = findViewById(R.id.toolbarSettings)
        button.visibility = View.INVISIBLE
        toolbarSettings.setNavigationOnClickListener { onBackPressed() }
        searchButton()
        updateData()
        if (savedInstanceState != null) {
            savedText = savedInstanceState.getString((search_text), "")
            search.setText(savedText)
        }
        textWatcher()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedText = search.text.toString()
        outState.putString((search_text), savedText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedText = savedInstanceState.getString((search_text), "")
        search.setText(savedText)
    }

    private fun textWatcher() {
        val myTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                button.visibility = View.INVISIBLE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    button.visibility = View.INVISIBLE
                } else {
                    button.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        search.doOnTextChanged { text, _, _, _ ->
            myTextWatcher.onTextChanged(text, 0, 0, 0)
        }
        button.setOnClickListener {
            search.text.clear()
            hideKeyboard()
            hidePicture()
            clearAdapter()
        }
    }

    private fun hidePicture(){
        iVMessage.visibility = View.INVISIBLE
        tvMessage.visibility = View.INVISIBLE
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(button.windowToken, 0)
    }

    private fun clearAdapter(){
        val clearAdapter = TrackAdapter(this)
        clearAdapter.clearListAdapter()
        rv.adapter = clearAdapter
    }
    private fun updateData(){
        btnMessage.setOnClickListener {getWebRequest()}
    }

    private fun searchButton() {
        search.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                clearAdapter()
                getWebRequest()
            }
            return@setOnEditorActionListener false
        }
    }
    private fun getWebRequest(){
        val query = search.text.toString().trim()
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
            rv.visibility = View.VISIBLE
            iVMessage.visibility = View.INVISIBLE
            btnMessage.visibility = View.INVISIBLE
            tvMessage.text = ""

            val trackAdapter = TrackAdapter(this@SearchActivity)
            rv.layoutManager = LinearLayoutManager(this@SearchActivity)
            rv.adapter = trackAdapter
            trackAdapter.updateData(trackList)

        } else {
            rv.visibility = View.INVISIBLE
            iVMessage.visibility = View.VISIBLE
            tvMessage.visibility = View.VISIBLE
            btnMessage.visibility = View.INVISIBLE

            if (response.isSuccessful) {
                tvMessage.text = noContent
                iVMessage.setImageResource(
                    if (isNightModeEnabled()) R.drawable.dark_no_content
                    else R.drawable.no_content
                )
            } else {
                tvMessage.text = noWeb
                iVMessage.setImageResource(
                    if (isNightModeEnabled()) R.drawable.dark_no_content
                    else R.drawable.no_content
                )
            }
        }
    }

    private fun handleFailure() {
        rv.visibility = View.INVISIBLE
        iVMessage.visibility = View.VISIBLE
        tvMessage.visibility = View.VISIBLE
        btnMessage.visibility = View.VISIBLE
        tvMessage.text = noWeb
        iVMessage.setImageResource(
            if (isNightModeEnabled()) R.drawable.dark_no_web
            else R.drawable.no_web
        )
    }

    private fun isNightModeEnabled(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }



}