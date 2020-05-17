package com.movieboss.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movieboss.pojo.movies.GenresItem

@Database(entities = [GenresItem::class], version = 1)
abstract class GenresDb : RoomDatabase() {
    abstract fun genresDao(): GenresDao

    companion object {
        var db: GenresDb? = null

        fun getGenresDbInstance(applicationContext: Context): GenresDao? {
            if (db == null) {
                db = Room.databaseBuilder(
                    applicationContext,
                    GenresDb::class.java, "genres_db"
                ).build()
            }
            return db?.genresDao()
        }
    }


}