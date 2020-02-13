package com.sweetlime.moviescollector.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sweetlime.moviescollector.BuildConfig
import com.sweetlime.moviescollector.api.MovieApiService
import com.sweetlime.moviescollector.db.MovieRoomDB
import com.sweetlime.moviescollector.api.model.MovieApi
import com.sweetlime.moviescollector.api.model.SearchItem
import com.sweetlime.moviescollector.api.model.SearchResponse
import com.sweetlime.moviescollector.db.dao.MovieDao
import com.sweetlime.moviescollector.db.entity.Movie
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MovieRepository(application: Application) {

    val movieDao: MovieDao = MovieRoomDB.getDatabase(application).movieDao()
    val allMovies: LiveData<List<Movie>> = movieDao.getMovies()
    val apiMovies = MutableLiveData<List<SearchItem>>()
    var retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var apiService = retrofit.create(MovieApiService::class.java)

    suspend fun insert(movie: Movie){
        movieDao.insert(movie)
    }

    fun getApiMovies(){
        apiService.getMovies("603",
            BuildConfig.API_KEY
        ).enqueue(object : Callback<MovieApi>{
            override fun onFailure(call: Call<MovieApi>, t: Throwable) {
                call.cancel()
            }

            override fun onResponse(call: Call<MovieApi>, response: Response<MovieApi>) {
                Log.d("Repo", response.body().toString())
                var movie = Movie(response.body()!!.id, response.body()!!.title, response.body()!!.overview,response.body()!!.posterPath)

                GlobalScope.async {
                    insert(movie)
                }
            }
        })
    }

    fun searchApiMovies(query: String){
        apiService.searchMovies(query,
            BuildConfig.API_KEY
        ).enqueue(object : Callback<SearchResponse>{
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                call.cancel()
            }

            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                Log.d("Repo", response.body().toString())

                response.body()?.results.let {
                    apiMovies.postValue(it)
                }
            }
        })
    }
}