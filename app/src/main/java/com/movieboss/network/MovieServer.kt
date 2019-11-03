package com.movieboss.network

import com.movieboss.pojo.movies.popular.MoviesInfoPopular
import com.movieboss.pojo.movies.toprated.MoviesInfoTopRated
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieServer {

    @GET("movie/popular?api_key=c7730ae5397362f560c5a42559525cfa&language=en-US")
    fun getPopularMovies(@Query("page") moviePage : Int) : Call<MoviesInfoPopular>

    @GET("movie/top_rated?api_key=c7730ae5397362f560c5a42559525cfa&language=en-US")
    fun getTopMovies(@Query("page") moviePage: Int) : Call<MoviesInfoTopRated>
}