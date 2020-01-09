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

class TopMovieAdapter(private val context: Context) : RecyclerView.Adapter<TopMovieViewHolder>() {
    private var topMovies : List<MovieResult> = ArrayList()

    fun setTopMovies(topMovies : List<MovieResult>) {
        this.topMovies = topMovies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_screen_movie_item, null)
        return TopMovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topMovies.size
    }

    override fun onBindViewHolder(holder: TopMovieViewHolder, position: Int) {
        holder.title.text = topMovies[position].title
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/${Constants.POSTER_SIZE}${topMovies[position].posterPath}")
            .into(holder.poster_image)
        holder.view.setOnClickListener {
            showMovieDetails(topMovies[position], context)
        }
    }
}

class TopMovieViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById<TextView>(R.id.title)
    val poster_image = view.findViewById<ImageView>(R.id.poster_image)
}