package com.thuraaung.trailersapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.thuraaung.trailersapp.R
import com.thuraaung.trailersapp.databinding.LayoutMovieItemBinding
import com.thuraaung.trailersapp.model.Movie

class MoviesAdapter(private val itemClickListener : (Movie?) -> Unit)
    : PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutMovieItemBinding.inflate(layoutInflater,parent,false)
        return MovieViewHolder(binding)
    }

    inner class MovieViewHolder(private val binding : LayoutMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { itemClickListener.invoke(getItem(adapterPosition)) }
        }

        fun bind(movie : Movie) {
            binding.tvMovie.text = movie.title
            binding.imgPoster.load(movie.posterUrl) {
                crossfade(true)
                placeholder(R.drawable.poster_placeholder)
            }
        }

    }

    companion object {

        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}

