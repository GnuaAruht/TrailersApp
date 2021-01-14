package com.thuraaung.trailersapp.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.thuraaung.trailersapp.data.MovieRepository
import com.thuraaung.trailersapp.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieViewModel @ViewModelInject constructor(private val repository : MovieRepository) : ViewModel() {

    fun getMovieList() : Flow<PagingData<Movie>> {
        return repository.getMoviesList()
    }
}
