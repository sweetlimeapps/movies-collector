package com.sweetlime.moviescollector.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sweetlime.moviescollector.R
import com.sweetlime.moviescollector.db.entity.Movie
import kotlinx.android.synthetic.main.card_my_movie.view.*

class MyMoviesAdapter() : RecyclerView.Adapter<MyMoviesAdapter.MyMovieViewHolder>() {
    var movies: List<Movie> = emptyList()

    fun setData(movies: List<Movie>){
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMovieViewHolder {
        return MyMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_my_movie, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MyMovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.itemView.titleTextView.text = movie.name

        val picasso = Picasso.get()
        picasso.load("https://image.tmdb.org/t/p/w500/" + movie.posterPath).into(holder.itemView.posterImageView)
    }

    class MyMovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}