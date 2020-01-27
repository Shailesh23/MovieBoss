package com.movieboss.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.movieboss.pojo.movies.MovieResult

@Dao
interface MovieDao {
    @Query("select * from MovieResult")
    fun getAllFavMovies() : List<MovieResult>

    @Insert
    fun insertMovie(movieResult: MovieResult)
}