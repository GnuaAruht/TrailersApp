package com.thuraaung.trailersapp.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thuraaung.trailersapp.LoadingDialog
import com.thuraaung.trailersapp.R
import com.thuraaung.trailersapp.api.DataResult
import com.thuraaung.trailersapp.databinding.ActivityMovieDetailBinding
import com.thuraaung.trailersapp.model.MovieDetail
import com.thuraaung.trailersapp.utils.EventObserver
import com.thuraaung.trailersapp.utils.createChip
import com.thuraaung.trailersapp.vm.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
import coil.load

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private val movieDetailViewModel : MovieDetailViewModel by viewModels()
    private lateinit var binding : ActivityMovieDetailBinding
    private var loadingDialog : DialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        observeMovieDetail()
        observeMovieVideo()

    }

    private fun observeMovieVideo() {
        // Observe event movie video event
        movieDetailViewModel.eventMovieVideo.observe(this, EventObserver{ movieVideo ->
            movieVideo.movieUrl?.let {
                openTrailer(it)
            }
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            //Check if the view is collapsed
            if (abs(verticalOffset) >= binding.appBar.totalScrollRange) {
                binding.collapsingToolbar.title = "Movie Detail"
            } else {
                binding.collapsingToolbar.title = ""
            }
        })
    }

    private fun observeMovieDetail() {

        movieDetailViewModel.movieDetailResult.observe(this,{ result ->
            when(result) {
                DataResult.Loading -> {
                    showLoading()
                }
                is DataResult.Error -> {
                    dismissLoading()
                    showError("Loading movie detail failed!")
                }
                is DataResult.Success -> {
                    dismissLoading()
                    showDetail(result.result)
                }
            }
        })
    }

    private fun showDetail(movieDetail : MovieDetail) {

        binding.tvTitle.text = movieDetail.title
        binding.tvYear.text = movieDetail.releaseYear
        binding.tvDuration.text = movieDetail.duration
        binding.tvRating.text = movieDetail.rating
        binding.tvOverview.text = movieDetail.overview

        binding.imgPoster.load(movieDetail.posterUrl) {
            crossfade(true)
            placeholder(R.drawable.poster_placeholder)
        }

        for(genre in movieDetail.genres) {
            val chip = createChip(genre.name)
            binding.chipGroup.addView(chip)
        }

        binding.fabPlay.setOnClickListener {
            movieDetailViewModel.openMovieTrailer()
        }
    }

    private fun showLoading() {

        LoadingDialog().run {
            show(supportFragmentManager,"")
            loadingDialog = this
        }
    }

    private fun dismissLoading() {
        loadingDialog?.dismiss()
    }

    private fun showError(message : String) {
        MaterialAlertDialogBuilder(this,R.style.ThemeOverlay_App_AlertDialog)
            .setTitle("Error")
            .setMessage(message)
            .setNegativeButton("Cancel") { dialog , _ -> }

            .setPositiveButton("OK") { dialog , _ ->
                dialog.dismiss()
            }.show()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openTrailer(url : String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}