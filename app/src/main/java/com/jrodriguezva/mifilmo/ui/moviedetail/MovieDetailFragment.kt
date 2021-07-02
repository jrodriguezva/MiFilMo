package com.jrodriguezva.mifilmo.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.FragmentMovieDetailBinding
import com.jrodriguezva.mifilmo.ui.main.MainActivity
import com.jrodriguezva.mifilmo.ui.moviedetail.adapter.MessagesViewPagerAdapter
import com.jrodriguezva.mifilmo.ui.moviedetail.adapter.MoviePeoplesAdapter
import com.jrodriguezva.mifilmo.utils.extensions.fromMinutesToHHmm
import com.jrodriguezva.mifilmo.utils.extensions.loadBackdrop
import com.jrodriguezva.mifilmo.utils.extensions.loadPoster
import com.jrodriguezva.mifilmo.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private var fragmentBinding: FragmentMovieDetailBinding? = null
    private val adapter = MoviePeoplesAdapter()
    private val args by navArgs<MovieDetailFragmentArgs>()
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMovieDetailBinding.bind(view)
        fragmentBinding = binding
        setBindings(binding)
        setObservers(binding)
    }

    private fun setObservers(binding: FragmentMovieDetailBinding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.spinner.collect {
                        binding.peopleGroup.visible = !it
                    }
                }
                launch {
                    viewModel.peoples.collect {
                        adapter.submitList(it)
                    }
                }
                launch {
                    viewModel.movie.collect {
                        binding.cover.loadPoster(it.posterPath.orEmpty(), 100)
                        binding.toolbarImage.loadBackdrop(it.backdropPath.orEmpty())
                        binding.overview.text = it.overview
                        binding.title.text = it.title
                        binding.rating.rating = it.voteAverage / 2
                        binding.time.fromMinutesToHHmm(it.runtime ?: 0)
                    }
                }
            }
        }
    }

    private fun setBindings(binding: FragmentMovieDetailBinding) {
        binding.recyclerPeople.adapter = adapter
        val navController = findNavController()
        binding.home.setOnClickListener {
            navController.navigateUp()
        }

        val adapterViewpager = MessagesViewPagerAdapter(args.movieId, this)
        binding.viewPager.adapter = adapterViewpager

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.my_messages)

                else -> getString(R.string.all_messages)

            }
        }.attach()

        binding.floatingActionButton.setOnClickListener { navigateToDialog() }
    }

    private fun navigateToDialog() {
        val dir = MovieDetailFragmentDirections.actionMovieDetailFragmentToPushMessageFragment(
            args.movieId
        )
        findNavController().navigate(dir)
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
        (activity as MainActivity).visibilityBottomNavigation(true)
    }

    override fun onResume() {
        (activity as MainActivity).visibilityBottomNavigation(false)
        super.onResume()
    }
}