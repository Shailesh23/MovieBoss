package com.movieboss.network

import com.movieboss.pojo.movies.popular.MoviesInfoPopular
import retrofit2.Call
import retrofit2.http.GET

interface MovieServer {

    @GET("movie/popular?api_key=c7730ae5397362f560c5a42559525cfa&language=en-US&page=1")
    fun getPopularMovies() : Call<MoviesInfoPopular>
}