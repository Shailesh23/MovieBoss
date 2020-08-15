package com.themovieguide.pojo.movies

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GenresItem(

	@field:SerializedName("name")
	var name: String? = null,

	@PrimaryKey
	@field:SerializedName("id")
	var id: Int? = 0
)
