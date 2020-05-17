package com.movieboss.pojo.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Genres(

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null
)

