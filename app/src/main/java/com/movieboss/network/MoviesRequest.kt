package com.movieboss.network

import androidx.lifecycle.MutableLiveData
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
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

    fun getPopularMovies(
        moviePage: Int,
        popularMovies: MutableLiveData<java.util.ArrayList<MovieResult>>
    ) {
        val call = movieServer.getPopularMovies(moviePage)

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
                    if (popularMovies.value != null) {
                        tempData.addAll(popularMovies.value!!)
                    }
                    tempData.addAll(response.body()?.results!!)
                    popularMovies.value = tempData
                }
            }

        })
    }

    fun getUpComingMovies(
        moviePage: Int,
        upComingMovies: MutableLiveData<java.util.ArrayList<MovieResult>>
    ) {
        val call = movieServer.getUpComingMovies(moviePage)

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
                    if (upComingMovies.value != null) {
                        tempData.addAll(upComingMovies.value!!)
                    }
                    tempData.addAll(response.body()?.results!!)
                    upComingMovies.value = tempData
                }
            }

        })
    }

    fun getTopRatedMovies(
        moviePage: Int,
        topMovies: MutableLiveData<ArrayList<MovieResult>>
    ): MutableLiveData<ArrayList<MovieResult>> {
        val call = movieServer.getTopMovies(moviePage)
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