package com.thuraaung.trailersapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thuraaung.trailersapp.R
import com.thuraaung.trailersapp.adapter.MovieLoadStateAdapter
import com.thuraaung.trailersapp.adapter.MoviesAdapter
import com.thuraaung.trailersapp.model.Movie
import com.thuraaung.trailersapp.vm.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel : MovieViewModel by viewModels()

    private val movieAdapter = MoviesAdapter { movie : Movie? ->
        startActivity(Intent(this@MainActivity, MovieDetailActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolBar = findViewById<Toolbar>(R.id.tool_bar)
        setSupportActionBar(toolBar)
        
        val rvMovie = findViewById<RecyclerView>(R.id.rv_movie)
        rvMovie.apply { 
            layoutManager = GridLayoutManager(this@MainActivity,3)
            adapter = movieAdapter.withLoadStateHeaderAndFooter(
                    header = MovieLoadStateAdapter { movieAdapter.retry() },
                    footer = MovieLoadStateAdapter { movieAdapter.retry() }
            )
        }

        lifecycleScope.launch {
            viewModel.getMovieList().collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }
}