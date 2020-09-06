package com.themovieguide.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.themovieguide.pojo.movies.Genres
import com.themovieguide.pojo.movies.Movies
import com.themovieguide.pojo.movies.MovieResult
import com.themovieguide.pojo.movies.VideoInfo
import com.themovieguide.utils.Logs
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
        mediaType : String,
        movieLiveData: MutableLiveData<java.util.ArrayList<MovieResult>>,
        originalLang : String
    ) {
        val call = movieServer.getMoviesData(mediaType, movieRequestType, moviePage, originalLang)

        call.enqueue(object : Callback<Movies> {
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Logs.e("MoviesRequest", t.message ?: "")
            }

            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {
                if (response.isSuccessful) {
                    val results = response.body()?.results!!
                    if (mediaType == "tv") {
                        results.forEach {
                            it.isTvSeries = true
                        }
                    }
                    val tempData = ArrayList<MovieResult>()
                    if (movieLiveData.value != null) {
                        tempData.addAll(movieLiveData.value!!)
                    }
                    tempData.addAll(results)
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

    fun getVideoInfo(handleResult : (VideoInfo) -> Unit, mediaType : String, mediaId : String) {
        val call = movieServer.getVideoInfo(mediaType, mediaId)

        call.enqueue(object : Callback<VideoInfo>{
            override fun onFailure(call: Call<VideoInfo>, t: Throwable) {
                Log.e("MovieRequest", t.message ?: "video call failed")
            }

            override fun onResponse(call: Call<VideoInfo>, response: Response<VideoInfo>) {
                if(response.isSuccessful) {
                    handleResult.invoke(response.body()!!)
                }
            }

        })
    }
}