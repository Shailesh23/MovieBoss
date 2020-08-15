package com.themovieguide.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.themovieguide.R
import com.themovieguide.pojo.movies.MovieResult
import com.themovieguide.utils.Constants
import com.themovieguide.utils.RemoteConfig
import com.themovieguide.utils.showMovieDetails

class MovieAdapter(private val context : Context) : RecyclerView.Adapter<HomeScreenViewHolder>() {

    private var listOfMovieMovies : List<MovieResult> = ArrayList()

    fun setMovies(movieMovies: List<MovieResult>) {
        val filteredMovies = movieMovies.filter { !it.posterPath.isNullOrEmpty() }
        listOfMovieMovies = filteredMovies
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
//        holder.posterTitle.text = listOfMovieMovies[position].title
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/${RemoteConfig.fetchConfig(Constants.HD_POSTER_SIZE_KEY)}" +
                    "${listOfMovieMovies[position].posterPath}")
            .into(holder.posterImage)
        holder.view.setOnClickListener {
            showMovieDetails(listOfMovieMovies[position], context)
        }
    }
}

class HomeScreenViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val posterImage : ImageView = view.findViewById(R.id.poster_image)
//    val posterTitle : TextView = view.findViewById(R.id.title)
}

