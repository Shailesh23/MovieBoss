package com.movieboss.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.MovieListConverter

@Database(entities = arrayOf(MovieResult::class), version = 1)
abstract class MovieDatabase : RoomDatabase() {
    //TODO add context through dagger
    abstract fun movieDao(): MovieDao

    companion object {
        var db: MovieDatabase? = null

        fun getMovieDbInstance(applicationContext: Context): MovieDao? {
            if (db == null) {
                db = Room.databaseBuilder(
                    applicationContext,
                    MovieDatabase::class.java, "fav_movies"

                ).build()
            }
            return db?.movieDao()
        }
    }
}