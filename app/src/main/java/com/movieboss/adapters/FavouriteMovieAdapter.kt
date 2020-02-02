package com.movieboss.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movieboss.MovieClickListener
import com.movieboss.R
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.Constants
import com.movieboss.utils.showMovieDetails

class FavouriteMovieAdapter constructor(val movieClickListener: MovieClickListener):
    RecyclerView.Adapter<FavouriteMovieAdapter.FavouriteMovieViewHolder>() {

    private lateinit var favouriteMovies: List<MovieResult>
    fun setFavourite(favouriteMovies : List<MovieResult>) {
        this.favouriteMovies = favouriteMovies
    }

    override fun getItemCount() : Int {
        return if(::favouriteMovies.isInitialized) {
            favouriteMovies.size
        } else {
            0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favourite_item_list, null)
        return FavouriteMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteMovieViewHolder, position: Int) {
        val context = holder.poster.context
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/${Constants.POSTER_SIZE}${favouriteMovies[position].posterPath}")
            .into(holder.poster)

        holder.poster.setOnClickListener {
            showMovieDetails(favouriteMovies[position], context)
        }

        holder.favouriteToggle.setOnClickListener {
            movieClickListener.movieSelected(favouriteMovies[position])
        }
    }

    class FavouriteMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster = itemView.findViewById<ImageView>(R.id.movie_poster)
        val favouriteToggle = itemView.findViewById<ImageView>(R.id.favorite_toggle)
    }
}