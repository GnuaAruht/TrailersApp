package com.thuraaung.trailersapp.model

import com.google.gson.annotations.SerializedName
import com.thuraaung.trailersapp.utils.Constants

data class MovieDetail(
    val id : Int,
    val title : String,
    @field:SerializedName("poster_path")
    val posterPath : String?,
    @field:SerializedName("release_date")
    val releaseDate : String,
    @field:SerializedName("vote_average")
    val voteAverage : Float,
    @field:SerializedName("genres")
    val genres : List<Genre>,
    @field:SerializedName("overview")
    val overview : String?,
    @field:SerializedName("runtime")
    val runtime : Int
) {

    val posterUrl : String?
        get() = if (posterPath != null) "${Constants.IMAGE_BASE_URL}$posterPath" else null

    val releaseYear : String
        get() = releaseDate.split('-')[0]

    val duration : String
        get() = "${runtime / 60}hr ${runtime % 60}min"

    val rating : String
        get() = "$voteAverage/10"

}