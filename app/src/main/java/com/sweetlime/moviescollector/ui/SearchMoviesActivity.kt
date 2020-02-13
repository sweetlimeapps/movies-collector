package com.sweetlime.moviescollector.ui

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweetlime.moviescollector.R
import com.sweetlime.moviescollector.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_search_movies.*


class SearchMoviesActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movies)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        initViews()
        initListeners()
        initObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    private fun initViews(){
        searchMoviesRecyclerView.layoutManager = LinearLayoutManager(this)
        searchMoviesRecyclerView.adapter = SearchMoviesAdapter(this)
        progressBar.visibility = View.GONE

        supportActionBar?.let {actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
        }
    }

    private fun initObservers(){
        movieViewModel.apiMovies.observe(this, Observer {

            progressBar.visibility = View.GONE
            if(!it.isNullOrEmpty()) {
                (searchMoviesRecyclerView.adapter as SearchMoviesAdapter).setData(it)
            } else {
                Toast.makeText(this,"No results", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initListeners(){
        searchMovieImageButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            movieViewModel.searchMovie(searchMovieEditText.text.toString())
        }

        searchMovieEditText.setOnEditorActionListener { _, actionId, _ ->
            searchEditorListener(actionId)
        }
    }

    private fun searchEditorListener(actionId: Int):Boolean{
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            progressBar.visibility = View.VISIBLE
            movieViewModel.searchMovie(searchMovieEditText.text.toString())
            hideKeyboard()
            return true
        }
        return false
    }

    private fun hideKeyboard(){
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0)
    }
}
