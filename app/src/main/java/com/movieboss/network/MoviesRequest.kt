package com.movieboss.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.movieboss.pojo.movies.popular.MoviesInfoPopular
import com.movieboss.pojo.movies.popular.ResultPopular
import com.movieboss.pojo.movies.toprated.MoviesInfoTopRated
import com.movieboss.pojo.movies.toprated.ResultTopRated
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRequest {

    private val movieServer: MovieServer
    private val pupularMovies = MutableLiveData<List<ResultPopular>>()
    private val topMovies = MutableLiveData<List<ResultTopRated>>()

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

    fun getPopularMovies(): MutableLiveData<List<ResultPopular>> {

        val call = movieServer.getPopularMovies()

        call.enqueue(object : Callback<MoviesInfoPopular> {
            override fun onFailure(call: Call<MoviesInfoPopular>, t: Throwable) {
                Log.e("Error", t.message)
            }

            override fun onResponse(
                call: Call<MoviesInfoPopular>,
                response: Response<MoviesInfoPopular>
            ) {
                if (response.isSuccessful) {
                    pupularMovies.value = response.body()?.results
                }
            }

        })

        return pupularMovies
    }

    fun getTopRatedMovies(): MutableLiveData<List<ResultTopRated>> {
        val call = movieServer.getTopMovies()

        call.enqueue(object : Callback<MoviesInfoTopRated> {
            override fun onFailure(call: Call<MoviesInfoTopRated>, t: Throwable) {
                Log.e("Error", t.message)
            }

            override fun onResponse(
                call: Call<MoviesInfoTopRated>,
                response: Response<MoviesInfoTopRated>
            ) {
                if(response.isSuccessful) {
                    topMovies.value = response.body()?.results
                }
            }

        })
        return topMovies
    }
}