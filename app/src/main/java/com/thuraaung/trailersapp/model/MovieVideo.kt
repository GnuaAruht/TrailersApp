package com.thuraaung.trailersapp.model

data class MovieVideoResponse(
    val id : Int,
    val results : List<MovieVideo>
)

data class MovieVideo(
    val id : String,
    val key : String,
    val name : String,
    val site : String,
    val size : Int,
    val type : String
) {
    val  movieUrl: String?
        get() =
            if(site == "YouTube") "https://www.youtube.com/watch?v=$key" else null
}