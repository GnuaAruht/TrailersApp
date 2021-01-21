package com.thuraaung.trailersapp.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.thuraaung.trailersapp.data.MovieRepository
import com.thuraaung.trailersapp.model.Movie
import com.thuraaung.trailersapp.model.MovieType
import kotlinx.coroutines.flow.Flow

class MovieViewModel @ViewModelInject constructor(private val repository : MovieRepository) : ViewModel() {

    private var currentMovieType = MovieType.NOW_PLAYING
    private var currentMovies : Flow<PagingData<Movie>>? = null

    @ExperimentalPagingApi
    fun getMoviesByType(newMovieType : MovieType) : Flow<PagingData<Movie>> {

        val latestResult = currentMovies
        if (currentMovieType == newMovieType && latestResult != null) {
            return latestResult
        }

        currentMovieType = newMovieType
        val newResult = repository.getMoviesByType(newMovieType)
            .cachedIn(viewModelScope)
        currentMovies = newResult
        return newResult
    }
}
