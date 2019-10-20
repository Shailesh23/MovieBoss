package com.movieboss.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.movieboss.pojo.MoviesInfo
import com.movieboss.pojo.Result
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesRequest {

    private val movieServer : MovieServer
    private val pupularMovies = MutableLiveData<List<Result>>()
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

    fun getPopularMovies() : MutableLiveData<List<Result>> {

        val call = movieServer.getPopularMovies()

        call.enqueue(object : Callback<MoviesInfo>{
            override fun onFailure(call: Call<MoviesInfo>, t: Throwable) {
                Log.e("Error", t.message)
            }

            override fun onResponse(call: Call<MoviesInfo>, response: Response<MoviesInfo>) {
               if(response.isSuccessful) {
                    pupularMovies.value = response.body()?.results
               }
            }

        })

        return pupularMovies
    }
}