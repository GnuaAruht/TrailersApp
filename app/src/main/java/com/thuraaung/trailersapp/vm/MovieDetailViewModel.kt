package com.thuraaung.trailersapp.vm

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thuraaung.trailersapp.Event
import com.thuraaung.trailersapp.data.MovieRepository
import com.thuraaung.trailersapp.model.MovieDetail
import com.thuraaung.trailersapp.model.MovieVideo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel @ViewModelInject constructor(
    private val repository : MovieRepository
) : ViewModel() {

    private val _movie = MutableLiveData<MovieDetail?>()
    val movie : LiveData<MovieDetail?>
        get() = _movie

    private val _eventMovieVideo = MutableLiveData<Event<MovieVideo>>()
    val eventMovieVideo : LiveData<Event<MovieVideo>>
        get() = _eventMovieVideo

    fun getMovieById(movieId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _movie.postValue(repository.getMovieById(movieId))
        }
    }

    fun openMovieTrailer(movieId : Int) {

        viewModelScope.launch {
            repository.getMovieVideo(movieId)?.let { movieVideo ->
                _eventMovieVideo.postValue(Event(movieVideo))
            }
        }

    }

}