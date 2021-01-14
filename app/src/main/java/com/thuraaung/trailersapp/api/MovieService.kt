package com.thuraaung.trailersapp.api

import com.thuraaung.trailersapp.BuildConfig
import com.thuraaung.trailersapp.model.MovieResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getMovieList(
        @Query("page")
        page : Int = 1 ) : MovieResponse

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.API_KEY
    }
}