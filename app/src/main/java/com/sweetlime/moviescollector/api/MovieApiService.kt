package com.sweetlime.moviescollector.api

import com.sweetlime.moviescollector.api.model.MovieApi
import com.sweetlime.moviescollector.api.model.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/{id}")
    fun getMovies(@Path("id") id: String, @Query("api_key") apiKey: String): Call<MovieApi>

    @GET("search/movie?language=en-US&page=1&include_adult=false")
    fun searchMovies(@Query("query") query: String, @Query("api_key") apiKey: String): Call<SearchResponse>
}