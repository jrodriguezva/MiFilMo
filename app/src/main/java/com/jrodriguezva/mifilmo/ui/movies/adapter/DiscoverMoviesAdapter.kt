package com.jrodriguezva.mifilmo.ui.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.ItemMovieBinding
import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.ui.movies.MovieListFragmentDirections
import com.jrodriguezva.mifilmo.utils.extensions.loadPoster

class DiscoverMoviesAdapter : ListAdapter<Movie, DiscoverMoviesAdapter.DiscoverMoviesViewHolder>(
    diffCallback
) {
    override fun onBindViewHolder(holder: DiscoverMoviesViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMoviesViewHolder {
        return DiscoverMoviesViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class DiscoverMoviesViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: Movie) {
            binding.apply {

                card.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.down_to_up)
                card.setOnClickListener {
                    navigateToDetail(item)
                }
                card.isChecked = item.checked
                image.loadPoster(item.posterPath.orEmpty(), 500)
                title.text = item.title
                date.text = item.releaseDate
                rating.setRating(item.voteAverage)
            }

        }

        private fun navigateToDetail(item: Movie) {
            val dir = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
                item.id
            )
            binding.root.findNavController().navigate(dir)
        }
    }
}

