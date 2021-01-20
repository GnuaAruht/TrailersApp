package com.thuraaung.trailersapp

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.chip.ChipGroup
import com.thuraaung.trailersapp.adapter.MovieLoadStateAdapter
import com.thuraaung.trailersapp.adapter.MoviesAdapter
import com.thuraaung.trailersapp.model.Genre

@BindingAdapter("adapter")
fun setAdapter(view : RecyclerView,moviesAdapter : MoviesAdapter) {

    view.apply {
        layoutManager = GridLayoutManager(view.context,3)
        adapter = moviesAdapter.withLoadStateHeaderAndFooter(
            header = MovieLoadStateAdapter { moviesAdapter.retry() },
            footer = MovieLoadStateAdapter { moviesAdapter.retry() })
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view : ImageView,url : String?) {

    view.load(url) {
        crossfade(true)
        placeholder(R.drawable.poster_placeholder)
    }
}


@BindingAdapter("chipData")
fun loadChipData(view : ChipGroup,genres : List<Genre>?) {

    genres?.let {
        for(genre in it) {
            val chip = view.context.createChip(genre.name)
            view.addView(chip)
        }
    }
}