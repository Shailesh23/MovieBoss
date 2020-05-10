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

class GridMovieAdapter constructor(private val movieClickListener: MovieClickListener?):
    RecyclerView.Adapter<GridMovieAdapter.FavouriteMovieViewHolder>() {

    private lateinit var gridMovieList: List<MovieResult>
    fun setGridMovie(gridMovies : List<MovieResult>) {
        this.gridMovieList = gridMovies
    }

    override fun getItemCount() : Int {
        return if(::gridMovieList.isInitialized) {
            gridMovieList.size
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
            .load("${Constants.BASE_URL}${Constants.POSTER_SIZE}${gridMovieList[position].posterPath}")
            .into(holder.poster)

        holder.poster.setOnClickListener {
            showMovieDetails(gridMovieList[position], context)
        }

        if(movieClickListener != null) {
            holder.favouriteToggle.setOnClickListener {
                movieClickListener.movieSelected(gridMovieList[position])
            }
        } else {
            holder.favouriteToggle.visibility = View.GONE
        }
    }

    class FavouriteMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster = itemView.findViewById<ImageView>(R.id.movie_poster)
        val favouriteToggle = itemView.findViewById<ImageView>(R.id.favorite_toggle)
    }
}