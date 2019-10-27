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
import com.movieboss.pojo.movies.popular.ResultPopular

class PopularMovieAdapter(private val context : Context) : RecyclerView.Adapter<HomeScreenViewHolder>() {

    lateinit var listOfPopularMovies : List<ResultPopular>

    fun setPopularMovies(popularMovies: List<ResultPopular>) {
        listOfPopularMovies = popularMovies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeScreenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_screen_movie_item, null)
        return HomeScreenViewHolder(view)
    }

    override fun getItemCount(): Int {
       return listOfPopularMovies.size
    }

    override fun onBindViewHolder(holder: HomeScreenViewHolder, position: Int) {
        holder.posterTitle.text = listOfPopularMovies[position].title
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w185${listOfPopularMovies[position].posterPath}")
            .into(holder.posterImage)

    }
}

class HomeScreenViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var posterImage : ImageView = view.findViewById(R.id.poster_image)
    var posterTitle : TextView = view.findViewById(R.id.title)
}

