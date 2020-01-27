package com.movieboss.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.movieboss.pojo.movies.MovieResult

class MovieListConverter {
    @TypeConverter
    fun stringToListConverter(listAsString: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson<List<Int>>(listAsString, listType)
    }

    @TypeConverter
    fun listToStringConverter(listOfMovies: List<Int>): String {
        return Gson().toJson(listOfMovies)
    }
}