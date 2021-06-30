package com.jrodriguezva.mifilmo.ui.movies.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.ItemMovieBinding
import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.ui.movies.MovieListFragmentDirections
import com.jrodriguezva.mifilmo.utils.extensions.loadPoster

class DiscoverMoviesAdapter(private val onClickFavorite: (Movie) -> Unit) :
    ListAdapter<Movie, DiscoverMoviesAdapter.DiscoverMoviesViewHolder>(diffCallback) {

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
                favorite.setOnClickListener {
                    item.favorite = !(item.favorite ?: false)
                    startAnimation(favorite, item)
                }
                if (item.favorite == true) {
                    favorite.setColorFilter(
                        ContextCompat.getColor(itemView.context, R.color.red_700),
                        android.graphics.PorterDuff.Mode.MULTIPLY
                    )
                } else {
                    favorite.setColorFilter(
                        ContextCompat.getColor(itemView.context, R.color.grey_700),
                        android.graphics.PorterDuff.Mode.MULTIPLY
                    )
                }
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

    private fun startAnimation(view: ImageView, character: Movie) {
        val rotationAnim = if (character.favorite == true) {
            ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
        } else {
            ObjectAnimator.ofFloat(view, "rotation", 360f, 0f)
        }.apply {
            duration = 300
            interpolator = AccelerateInterpolator()
        }

        val bounceAnimX = ObjectAnimator.ofFloat(view, "scaleX", 0.2f, 1f).apply {
            duration = 300
            interpolator = OvershootInterpolator()
        }

        val bounceAnimY = ObjectAnimator.ofFloat(view, "scaleY", 0.2f, 1f).apply {
            duration = 300
            interpolator = OvershootInterpolator()
        }

        val colorAnim = if (character.favorite == true) {
            ObjectAnimator.ofArgb(
                view, "colorFilter",
                ContextCompat.getColor(view.context, R.color.grey_700),
                ContextCompat.getColor(view.context, R.color.red_700)
            )
        } else {
            ObjectAnimator.ofArgb(
                view, "colorFilter",
                ContextCompat.getColor(view.context, R.color.red_700),
                ContextCompat.getColor(view.context, R.color.grey_700)
            )
        }.apply {
            duration = 600
            interpolator = AccelerateDecelerateInterpolator()
        }

        AnimatorSet().apply {
            play(rotationAnim).with(colorAnim)
            play(bounceAnimX).with(bounceAnimY).after(rotationAnim)
            doOnEnd {
                onClickFavorite(character)
            }
            start()
        }
    }
}

