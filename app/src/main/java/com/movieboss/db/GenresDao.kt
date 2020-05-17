package com.movieboss.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movieboss.pojo.movies.GenresItem

@Dao
interface GenresDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGenres(genresItem: GenresItem)

    @Query("select * from GenresItem")
    fun getGenres() : LiveData<List<GenresItem>>
}