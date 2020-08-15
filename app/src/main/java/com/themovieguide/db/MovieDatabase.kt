package com.themovieguide.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.themovieguide.pojo.movies.MovieResult
import com.themovieguide.utils.MovieListConverter

@Database(entities = [MovieResult::class], version = 2)
@TypeConverters(MovieListConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        var db: MovieDatabase? = null

        fun getMovieDbInstance(applicationContext: Context): MovieDao? {
            if (db == null) {
                db = Room.databaseBuilder(
                    applicationContext,
                    MovieDatabase::class.java, "fav_movies"

                ).fallbackToDestructiveMigration().build()
            }
            return db?.movieDao()
        }
    }
}