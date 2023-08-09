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

class SearchActivity : AppCompatActivity() {
    private lateinit var toolbarSettings: Toolbar
    private lateinit var search: EditText
    private lateinit var button: ImageButton
    private var savedText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        search = findViewById(R.id.search)
        button = findViewById(R.id.clear_button)
        toolbarSettings = findViewById(R.id.toolbarSettings)
        button.visibility = View.INVISIBLE
        toolbarSettings.setNavigationOnClickListener { onBackPressed() }

        if (savedInstanceState != null) {
            savedText = savedInstanceState.getString(getString(R.string.key_state), "")
            search.setText(savedText)
        }
        textWatcher()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedText = search.text.toString()
        outState.putString(getString(R.string.key_state), savedText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedText = savedInstanceState.getString(getString(R.string.key_state), "")
        search.setText(savedText)
    }

    fun textWatcher(){
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
        search.addTextChangedListener(myTextWatcher)
        button.setOnClickListener{
            search.text.clear()
            hideKeyboard()
        }
    }
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(button.windowToken, 0)
    }
}