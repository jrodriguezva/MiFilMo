package com.jrodriguezva.mifilmo.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.FragmentFavoriteMovieListBinding
import com.jrodriguezva.mifilmo.ui.movies.MovieListFragmentDirections
import com.jrodriguezva.mifilmo.ui.movies.adapter.DiscoverMoviesAdapter
import com.jrodriguezva.mifilmo.ui.settings.SettingsActivity
import com.jrodriguezva.mifilmo.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteListFragment : Fragment(R.layout.fragment_favorite_movie_list) {

    private var fragmentBinding: FragmentFavoriteMovieListBinding? = null
    private val viewModel by viewModels<FavoriteListViewModel>()
    private lateinit var adapter: DiscoverMoviesAdapter

    private val settingActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            viewModel.refreshData()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteMovieListBinding.bind(view)
        fragmentBinding = binding
        adapter = DiscoverMoviesAdapter(viewModel::onClickFavorite)
        setBindings(binding)
        setObservers()
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.movies.collect {
                        emptyViewVisibility(it.isEmpty())
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun emptyViewVisibility(visible: Boolean) {
        fragmentBinding?.emptyGroup?.visible = visible
        fragmentBinding?.recycler?.visible = !visible
    }

    private fun setBindings(binding: FragmentFavoriteMovieListBinding) {
        binding.recycler.adapter = adapter

        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    settingActivity.launch(Intent(requireContext(), SettingsActivity::class.java))
                    true
                }
                R.id.profile -> {
                    val dir = MovieListFragmentDirections.actionMovieListFragmentToProfileFragment()
                    findNavController().navigate(dir)
                    true
                }
                else -> false
            }
        }
    }


    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}