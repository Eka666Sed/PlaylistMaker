package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {
    private lateinit var toolbarSettings: Toolbar
    private lateinit var search: EditText
    private lateinit var button: ImageButton
    private var savedText: String = ""

    companion object {
        const val search_text = "search_text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        search = findViewById(R.id.search)
        button = findViewById(R.id.clear_button)
        toolbarSettings = findViewById(R.id.toolbarSettings)
        button.visibility = View.INVISIBLE
        toolbarSettings.setNavigationOnClickListener { onBackPressed() }

        if (savedInstanceState != null) {
            savedText = savedInstanceState.getString((search_text), "")
            search.setText(savedText)
        }
        textWatcher()
        getRecyclerView()
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
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(button.windowToken, 0)
    }

    private fun getRecyclerView(){
        val recyclerView: RecyclerView = findViewById(R.id.track_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TrackAdapter(addDataRecyclerView())
    }
    private fun addDataRecyclerView() : List<Track>{
        val listDataRV = ArrayList<Track>()
        listDataRV.add(
            Track(
                "Smells Like Teen Spirit",
                "Nirvana",
                "5:01",
                "https://is5-ssl.mzstatic.com/image/thumb/Music115/v4/7b/58/c2/7b58c21a-2b51-2bb2-e59a-9bb9b96ad8c3/00602567924166.rgb.jpg/100x100bb.jpg"
            )
        )
        listDataRV.add(
            Track(
                "Billie Jean",
                "Michael Jackson",
                "4:35",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/3d/9d/38/3d9d3811-71f0-3a0e-1ada-3004e56ff852/827969428726.jpg/100x100bb.jpg"
            )
        )
        listDataRV.add(
            Track(
                "Stayin' Alive",
                "Bee Gees",
                "4:10",
                "https://is4-ssl.mzstatic.com/image/thumb/Music115/v4/1f/80/1f/1f801fc1-8c0f-ea3e-d3e5-387c6619619e/16UMGIM86640.rgb.jpg/100x100bb.jpg"
            )
        )
        listDataRV.add(
            Track(
                "Whole Lotta Love",
                "Led Zeppelin",
                "5:33",
                "https://is2-ssl.mzstatic.com/image/thumb/Music62/v4/7e/17/e3/7e17e33f-2efa-2a36-e916-7f808576cf6b/mzm.fyigqcbs.jpg/100x100bb.jpg"
            )
        )
        listDataRV.add(
            Track(
                "Sweet Child O'Mine",
                "Guns N' Roses",
                "5:03",
                "https://is5-ssl.mzstatic.com/image/thumb/Music125/v4/a0/4d/c4/a04dc484-03cc-02aa-fa82-5334fcb4bc16/18UMGIM24878.rgb.jpg/100x100bb.jpg"
            )
        )
        return listDataRV
    }


}