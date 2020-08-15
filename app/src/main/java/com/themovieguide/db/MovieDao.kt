package com.themovieguide.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.themovieguide.pojo.movies.MovieResult

@Dao
interface MovieDao {
    @Query("select * from MovieResult")
    fun getAllFavMovies(): LiveData<List<MovieResult>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movieResult: MovieResult)

    @Delete
    fun deleteMovie(movie: MovieResult)
}