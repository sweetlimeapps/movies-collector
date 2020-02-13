package com.sweetlime.moviescollector.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sweetlime.moviescollector.api.model.MovieApi
import com.sweetlime.moviescollector.api.model.SearchItem
import com.sweetlime.moviescollector.repository.MovieRepository
import com.sweetlime.moviescollector.db.entity.Movie
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val movieRepository: MovieRepository = MovieRepository(application)

    val movies: LiveData<List<Movie>>
    val apiMovies: LiveData<List<SearchItem>>

    init {
        movies = movieRepository.allMovies
        apiMovies = movieRepository.apiMovies
    }

    fun insert(movie: Movie) = viewModelScope.launch {
        movieRepository.insert(movie)
    }

    fun getApiMovies() = viewModelScope.launch {
        //movieRepository.getApiMovies()

    }

    fun searchMovie(query: String){
        movieRepository.searchApiMovies(query)
    }
}