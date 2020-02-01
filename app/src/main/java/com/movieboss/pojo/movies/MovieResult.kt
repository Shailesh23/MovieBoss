package com.movieboss.pojo.movies

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.movieboss.utils.MovieListConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class MovieResult (
    @SerializedName("popularity")
    var popularity: Double = 0.toDouble(),
    @SerializedName("vote_count")
    var voteCount: Int = 0,
    @SerializedName("video")
    var isVideo: Boolean = false,
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @SerializedName("id")
    @PrimaryKey
    var id: Int = 0,
    @SerializedName("adult")
    var isAdult: Boolean = false,
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("vote_average")
    var voteAverage: Double = 0.toDouble(),
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("release_date")
    var releaseDate: String? = null

) : Parcelable