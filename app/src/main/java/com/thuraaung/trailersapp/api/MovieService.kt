package com.thuraaung.trailersapp.api

import com.thuraaung.trailersapp.BuildConfig
import com.thuraaung.trailersapp.api.MovieService.Companion.API_KEY
import com.thuraaung.trailersapp.model.Movie
import com.thuraaung.trailersapp.model.MovieDetail
import com.thuraaung.trailersapp.model.MovieResponse
import com.thuraaung.trailersapp.model.MovieVideoResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getMovieList(
        @Query("page")
        page : Int = 1 ) : MovieResponse


    @GET("movie/{movieId}?api_key=$API_KEY")
    suspend fun getMovie(@Path("movieId") movieId : Int) : Response<MovieDetail>

    @GET("movie/{movie_id}/videos?api_key=$API_KEY")
    suspend fun getMovieVideo(@Path("movie_id") movieId: Int) : Response<MovieVideoResponse>

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.API_KEY
    }
}