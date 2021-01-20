package com.thuraaung.trailersapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
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
import com.thuraaung.trailersapp.vm.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    @ExperimentalPagingApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            adapter = movieAdapter
        }
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        lifecycleScope.launch {
            viewModel.getMovieList().collectLatest {
                movieAdapter.submitData(it)
            }
        }

        movieAdapter.addLoadStateListener { loadState ->
            binding.rvMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
        }

    }
}