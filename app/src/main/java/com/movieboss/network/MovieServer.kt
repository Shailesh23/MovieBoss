package com.movieboss.network

import com.movieboss.pojo.movies.Genres
import com.movieboss.pojo.movies.Movies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieServer {

    @GET("movie/{type}?api_key=c7730ae5397362f560c5a42559525cfa&language=en-US")
    fun getMoviesData(@Path("type") movieType : String, @Query("page") moviePage : Int) : Call<Movies>

    @GET("search/movie?api_key=c7730ae5397362f560c5a42559525cfa&page=1&language=en-US")
    fun searchMovies(@Query("query") queryParam : String) : Call<Movies>

    @GET("genre/movie/list?api_key=c7730ae5397362f560c5a42559525cfa&language=en-US")
    fun getGenres() : Call<Genres>
}