package com.movieboss.network

import androidx.lifecycle.MutableLiveData
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.movieboss.pojo.movies.Genres
import com.movieboss.pojo.movies.Movies
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.utils.Logs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRequest {

    private val movieServer: MovieServer

    init {
        val gson = GsonBuilder().setFieldNamingPolicy(
            FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
        ).create()

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

        val retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/3/").build()

        movieServer = retrofit.create(MovieServer::class.java)
    }

    fun fetchMovieData(
        moviePage: Int,
        movieRequestType : String,
        movieLiveData: MutableLiveData<java.util.ArrayList<MovieResult>>
    ) {
        val call = movieServer.getMoviesData(movieRequestType, moviePage)

        call.enqueue(object : Callback<Movies> {
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Logs.e("MoviesRequest", t.message ?: "")
            }

            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {
                if (response.isSuccessful) {
                    val tempData = ArrayList<MovieResult>()
                    if (movieLiveData.value != null) {
                        tempData.addAll(movieLiveData.value!!)
                    }
                    tempData.addAll(response.body()?.results!!)
                    movieLiveData.value = tempData
                }
            }
        })
    }

    fun getSearchResults(
        queryParams: String,
        searchResults: MutableLiveData<ArrayList<MovieResult>>
    ): MutableLiveData<ArrayList<MovieResult>> {
        val call = movieServer.searchMovies(queryParams)
        call.enqueue(object : Callback<Movies> {
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Logs.e("MovieRequest", "search request failed ${t.message}")
            }

            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                if (response.isSuccessful) {
                    searchResults.value = response.body()?.results!!
                }
            }

        })

        return searchResults
    }

    fun updateGenresInfo(handleResult : (Genres) -> Unit) {
        val call = movieServer.getGenres()
        call.enqueue(object : Callback<Genres>{
            override fun onFailure(call: Call<Genres>, t: Throwable) {
                //todo log analytics here
            }

            override fun onResponse(call: Call<Genres>, response: Response<Genres>) {
                if(response.isSuccessful) {
                    handleResult(response.body()!!)
                }
            }

        })
    }
}