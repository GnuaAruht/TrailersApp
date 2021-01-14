package com.thuraaung.trailersapp.model

import com.google.gson.annotations.SerializedName
import com.thuraaung.trailersapp.model.Movie

data class MovieResponse(
    val page : Int,
    val results : List<Movie>,
    @SerializedName("total_pages")
    val totalPages : Int,
    @SerializedName("total_results")
    val totalResults : Int
)
