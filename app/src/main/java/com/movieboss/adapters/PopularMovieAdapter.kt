package com.movieboss.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movieboss.R
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.Constants
import com.movieboss.utils.showMovieDetails

class PopularMovieAdapter(private val context : Context) : RecyclerView.Adapter<HomeScreenViewHolder>() {

    var listOfMovieMovies : List<MovieResult> = ArrayList()

    fun setPopularMovies(movieMovies: List<MovieResult>) {
        listOfMovieMovies = movieMovies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_screen_movie_item, null)
        view.setOnClickListener{

        }
        return HomeScreenViewHolder(view)
    }

    override fun getItemCount(): Int {
       return listOfMovieMovies.size
    }

    override fun onBindViewHolder(holder: HomeScreenViewHolder, position: Int) {
        holder.posterTitle.text = listOfMovieMovies[position].title
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/${Constants.HD_POSTER_SIZE}${listOfMovieMovies[position].posterPath}")
            .into(holder.posterImage)
        holder.view.setOnClickListener {
            showMovieDetails(listOfMovieMovies[position], context)
        }
    }
}

class HomeScreenViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val posterImage : ImageView = view.findViewById(R.id.poster_image)
    val posterTitle : TextView = view.findViewById(R.id.title)
}

