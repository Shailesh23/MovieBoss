package com.movieboss.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.movieboss.pojo.movies.popular.MoviesInfoPopular
import com.movieboss.pojo.movies.MovieResult
import com.movieboss.pojo.movies.toprated.MoviesInfoTopRated
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRequest {

    private val movieServer: MovieServer
    private val popularMovies = MutableLiveData<java.util.ArrayList<MovieResult>>()
    private val topMovies = MutableLiveData<ArrayList<MovieResult>>()

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

    fun getPopularMovies(moviePage: Int): MutableLiveData<ArrayList<MovieResult>> {

        val call = movieServer.getPopularMovies(moviePage)

        call.enqueue(object : Callback<MoviesInfoPopular> {
            override fun onFailure(call: Call<MoviesInfoPopular>, t: Throwable) {
                Log.e("Error", t.message)
            }

            override fun onResponse(
                call: Call<MoviesInfoPopular>,
                response: Response<MoviesInfoPopular>
            ) {
                if (response.isSuccessful) {
                    val tempData = ArrayList<MovieResult>()
                    if (popularMovies.value != null) {
                        tempData.addAll(popularMovies.value!!)
                    }
                    tempData.addAll(response.body()?.results!!)
                    popularMovies.value = tempData
                }
            }

        })

        return popularMovies
    }

    fun getTopRatedMovies(moviePage : Int): MutableLiveData<ArrayList<MovieResult>> {
        val call = movieServer.getTopMovies(moviePage)

        call.enqueue(object : Callback<MoviesInfoTopRated> {
            override fun onFailure(call: Call<MoviesInfoTopRated>, t: Throwable) {
                Log.e("Error", t.message)
            }

            override fun onResponse(
                call: Call<MoviesInfoTopRated>,
                response: Response<MoviesInfoTopRated>
            ) {
                if (response.isSuccessful) {
                    val tempData = ArrayList<MovieResult>()
                    if (topMovies.value != null) {
                        tempData.addAll(topMovies.value!!)
                    }
                    tempData.addAll(response.body()?.results!!)
                    topMovies.value = tempData
                }
            }

        })
        return topMovies
    }
}