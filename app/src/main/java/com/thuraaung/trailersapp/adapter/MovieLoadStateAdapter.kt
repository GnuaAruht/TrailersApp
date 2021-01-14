package com.thuraaung.trailersapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

//    private val tvLoadingError = view.findViewById<TextView>(R.id.tv_loading_error)
//    private val btnReload = view.findViewById<Button>(R.id.btn_reload)
//    private val loadingProgress = view.findViewById<ProgressBar>(R.id.loading_progress)

    init {
        view.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState : LoadState) {

//        if (loadState is LoadState.Error) {
//            tvLoadingError.text = "Loading Error"
//        }
//        loadingProgress.isVisible(loadState is LoadState.Loading)
//        btnReload.isVisible(loadState !is LoadState.Loading)
//        tvLoadingError.isVisible(loadState !is LoadState.Loading)
    }

    companion object {
        fun create(parent : ViewGroup,retry: () -> Unit) : MovieLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.movies_load_state_footer_view,parent,false)
            return MovieLoadStateViewHolder(view,retry)
        }
    }
}