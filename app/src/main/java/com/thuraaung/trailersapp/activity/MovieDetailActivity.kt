package com.thuraaung.trailersapp.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.thuraaung.trailersapp.EventObserver
import com.thuraaung.trailersapp.databinding.ActivityMovieDetailBinding
import com.thuraaung.trailersapp.vm.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private val movieDetailViewModel : MovieDetailViewModel by viewModels()
    private val movieId : Int by lazy {
        intent.getIntExtra("movie_id",0)
    }
    private lateinit var binding : ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieDetailBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MovieDetailActivity
            viewModel = movieDetailViewModel
        }
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _ , verticalOffset ->
            //Check if the view is collapsed
            if (abs(verticalOffset) >= binding.appBar.totalScrollRange) {
                binding.collapsingToolbar.title = "Movie Detail"
            } else {
                binding.collapsingToolbar.title = ""
            }
        })

        movieDetailViewModel.getMovieById(movieId)
        // Observe event movie video event
        movieDetailViewModel.eventMovieVideo.observe(this,EventObserver{ movieVideo ->
            movieVideo.movieUrl?.let {
                openTrailer(it)
            }
        })

    }

    private fun openTrailer(url : String) {
        Toast.makeText(this,"Open movie trailer",Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}