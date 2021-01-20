package com.thuraaung.trailersapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thuraaung.trailersapp.R

class MovieLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<MovieLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: MovieLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MovieLoadStateViewHolder {
        return MovieLoadStateViewHolder.create(parent, retry)
    }
}

class MovieLoadStateViewHolder(view : View,retry : () -> Unit) : RecyclerView.ViewHolder(view) {

    private val imgRefresh = view.findViewById<ImageView>(R.id.img_refresh)
    private val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

    init {
        view.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState : LoadState) {
        progressBar.isVisible = loadState is LoadState.Loading
        imgRefresh.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent : ViewGroup,retry: () -> Unit) : MovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movies_load_state_footer_view,parent,false)
            return MovieLoadStateViewHolder(view,retry)
        }
    }
}