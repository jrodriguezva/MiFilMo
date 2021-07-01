package com.jrodriguezva.mifilmo.ui.moviedetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jrodriguezva.mifilmo.databinding.ItemPeopleBinding
import com.jrodriguezva.mifilmo.domain.model.People
import com.jrodriguezva.mifilmo.ui.moviedetail.MovieDetailFragmentDirections
import com.jrodriguezva.mifilmo.utils.extensions.loadProfile

class MoviePeoplesAdapter : ListAdapter<People, MoviePeoplesAdapter.DiscoverMoviesViewHolder>(
    diffCallback
) {
    override fun onBindViewHolder(holder: DiscoverMoviesViewHolder, position: Int) {
        holder.bindTo(getItem(position))

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMoviesViewHolder {
        return DiscoverMoviesViewHolder(
            ItemPeopleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<People>() {
            override fun areItemsTheSame(oldItem: People, newItem: People): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: People, newItem: People): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class DiscoverMoviesViewHolder(private val binding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: People) {
            binding.apply {
                image.loadProfile(item.profilePath.orEmpty())
                name.text = item.name
                character.text = item.character
                card.setOnClickListener { navigateToDetail(item) }
            }
        }

        private fun navigateToDetail(item: People) {
            val dir = MovieDetailFragmentDirections.actionMovieDetailFragmentToPersonFragment(
                item.peopleId
            )
            binding.root.findNavController().navigate(dir)
        }
    }

}

