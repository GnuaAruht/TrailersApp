package com.thuraaung.trailersapp.model

import com.google.gson.annotations.SerializedName

const val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/w185"

data class Movie(
    val id : Int,
    val title : String,
    val overview : String,
    @SerializedName("poster_path")
    val posterPath : String?
    ) {

    val posterUrl : String?
        get() = if (posterPath != null) "$IMAGE_BASE_URL$posterPath" else null
}