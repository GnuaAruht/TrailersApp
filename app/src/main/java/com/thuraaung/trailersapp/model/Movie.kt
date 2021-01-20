package com.thuraaung.trailersapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.thuraaung.trailersapp.Constants.IMAGE_BASE_URL

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey
    val id : Int,
    val title : String,
    @field:SerializedName("poster_path")
    val posterPath : String?,
    @field:SerializedName("release_date")
    val releaseDate : String,
    @field:SerializedName("vote_average")
    val voteAverage : Float
) {

    val posterUrl : String?
        get() = if (posterPath != null) "$IMAGE_BASE_URL$posterPath" else null

}