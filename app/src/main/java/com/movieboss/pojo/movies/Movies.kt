package com.movieboss.pojo.movies

import java.util.ArrayList
import com.google.gson.annotations.SerializedName

class Movies {
    @SerializedName("results")
    var results = ArrayList<MovieResult>()
}
