package com.sweetlime.moviescollector.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sweetlime.moviescollector.R
import com.sweetlime.moviescollector.api.model.SearchItem
import com.sweetlime.moviescollector.db.entity.Movie
import com.sweetlime.moviescollector.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.card_my_movie.view.*

class SearchMoviesAdapter(context: Context) : RecyclerView.Adapter<SearchMoviesAdapter.SearchMovieViewHolder>() {
    var movies: List<SearchItem> = emptyList()
    var movieViewModel: MovieViewModel = ViewModelProvider(context as AppCompatActivity).get(MovieViewModel::class.java)

    fun setData(movies: List<SearchItem>){
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        return SearchMovieViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_my_movie, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.itemView.titleTextView.text = movie.title

        val picasso = Picasso.get()
        picasso.load("https://image.tmdb.org/t/p/w500/" + movie.posterPath)
            .placeholder(R.drawable.ic_local_movies)
            .into(holder.itemView.posterImageView)

        holder.itemView.setOnClickListener {
            val dbMovie = Movie(
                movie.id,
                movie.title,
                movie.overview,
                movie.posterPath
            )
            movieViewModel.insert(dbMovie)
        }
    }

    class SearchMovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}