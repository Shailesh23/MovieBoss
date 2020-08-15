package com.themovieguide.utils

 class Constants {
    companion object {
        private const val POSTER_SIZE = "w185"
        private const val BACKDROP_SIZE = "w500"
        private const val HD_POSTER_SIZE = "w185"
        const val MOVIE_KEY = "movie_key"
        const val BASE_URL = "https://image.tmdb.org/t/p/"

        const val POSTER_SIZE_KEY = "POSTER_SIZE"
        const val BACKDROP_SIZE_KEY = "BACKDROP_SIZE"
        const val HD_POSTER_SIZE_KEY = "HD_POSTER_SIZE"

        fun fetchDefaultConfig() : Map<String, String> {
            val mapOfDefaults = mutableMapOf<String, String>()
            mapOfDefaults[BACKDROP_SIZE_KEY] = BACKDROP_SIZE
            mapOfDefaults[HD_POSTER_SIZE_KEY] = HD_POSTER_SIZE
            mapOfDefaults[POSTER_SIZE_KEY] = POSTER_SIZE
            return mapOfDefaults
        }
    }
}