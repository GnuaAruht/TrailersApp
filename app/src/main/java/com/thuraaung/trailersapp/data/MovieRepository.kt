package com.thuraaung.trailersapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thuraaung.trailersapp.api.MovieService
import com.thuraaung.trailersapp.model.Movie
import com.thuraaung.trailersapp.model.MovieDetail
import com.thuraaung.trailersapp.model.MovieType
import com.thuraaung.trailersapp.model.MovieVideo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service : MovieService
) {

    @ExperimentalPagingApi
    fun getMoviesByType(movieType : MovieType) : Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(service,movieType) }
        ).flow
    }
//
//    @ExperimentalPagingApi
//    fun getMoviesList() : Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 1,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { MoviePagingSource(service) }
//        ).flow
//    }
//
//    @ExperimentalPagingApi
//    fun getMoviesList() : Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = 1,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { MoviePagingSource(service) }
//        ).flow
//    }

    suspend fun getMovieById(movieId : Int) : MovieDetail? {

        return try {
            val response = service.getMovie(movieId)
            if (response.isSuccessful) {
                response.body()!!
            } else {
                null
            }
        } catch (e : Exception) {
            e.printStackTrace()
            null
        }
    }

}