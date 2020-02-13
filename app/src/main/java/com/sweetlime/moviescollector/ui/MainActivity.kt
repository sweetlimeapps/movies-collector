package com.sweetlime.moviescollector.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sweetlime.moviescollector.viewmodel.MovieViewModel
import com.sweetlime.moviescollector.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var movieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        movieViewModel.getApiMovies()

        myMoviesRecyclerView.layoutManager = LinearLayoutManager(this)
        myMoviesRecyclerView.adapter =
            MyMoviesAdapter()

        movieViewModel.movies.observe(this, Observer {
            Log.d("Repo", it.toString())

            (myMoviesRecyclerView.adapter as MyMoviesAdapter).setData(it)
        })

        addMoviActionButton.setOnClickListener {
            val intent = Intent(this,SearchMoviesActivity::class.java)
            startActivity(intent)
        }
    }
}
