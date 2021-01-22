package com.thuraaung.trailersapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.thuraaung.trailersapp.R
import com.thuraaung.trailersapp.adapter.MovieLoadStateAdapter
import com.thuraaung.trailersapp.adapter.MoviesAdapter
import com.thuraaung.trailersapp.databinding.ActivityMainBinding
import com.thuraaung.trailersapp.model.Movie
import com.thuraaung.trailersapp.model.MovieType
import com.thuraaung.trailersapp.vm.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : MovieViewModel by viewModels()
    private val movieAdapter = MoviesAdapter { movie : Movie? ->
        movie?.let {
            val intent = Intent(this@MainActivity, MovieDetailActivity::class.java).apply {
                putExtra("movie_id",it.id)
            }
            startActivity(intent)
        }
    }
    private lateinit var binding : ActivityMainBinding
    private var movieJob : Job? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        setUpAdapter()
        setUpView()
        observeMovieList(MovieType.NOW_PLAYING)
    }

    private fun setUpView() {

        binding.btnRetry.setOnClickListener {
            movieAdapter.retry()
        }

        binding.rvMovie.apply {
            layoutManager = GridLayoutManager(this@MainActivity,3)
            adapter = movieAdapter.withLoadStateHeaderAndFooter(
                header = MovieLoadStateAdapter { movieAdapter.retry() },
                footer = MovieLoadStateAdapter { movieAdapter.retry() })
        }
    }

    private fun setUpAdapter() {

        movieAdapter.addLoadStateListener { loadState ->
            binding.rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
        }

    }

    @ExperimentalPagingApi
    private fun observeMovieList(movieType: MovieType) {

        supportActionBar?.title = "${movieType.typeName} Movies"
//        binding.toolBar.title = "${movieType.typeName} Movies"
        movieJob?.cancel()
        movieJob = lifecycleScope.launch {
            viewModel.getMoviesByType(movieType).collectLatest {
                movieAdapter.submitData(it)
            }
        }

    }

    @ExperimentalPagingApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val movieType = when(item.itemId) {
            R.id.now_playing -> MovieType.NOW_PLAYING
            R.id.upcoming -> MovieType.UPCOMING
            R.id.top_rated -> MovieType.TOP_RATED
            else -> MovieType.NOW_PLAYING // Now playing movie type is default
        }
        observeMovieList(movieType)
        return super.onOptionsItemSelected(item)
    }
}