package com.jrodriguezva.mifilmo.ui.movies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.FragmentMovieListBinding
import com.jrodriguezva.mifilmo.ui.movies.adapter.DiscoverMoviesAdapter
import com.jrodriguezva.mifilmo.utils.extensions.endless
import com.jrodriguezva.mifilmo.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private var fragmentBinding: FragmentMovieListBinding? = null
    private val viewModel by activityViewModels<MovieListViewModel>()
    private val adapter = DiscoverMoviesAdapter()

    companion object {
        fun newInstance() = MovieListFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMovieListBinding.bind(view)
        fragmentBinding = binding
        setBindings(binding)
        setObservers(binding)
    }

    private fun setObservers(binding: FragmentMovieListBinding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading.collect {
                        binding.swipeToRefresh.isRefreshing = it
                    }
                }

                launch {
                    viewModel.movies.collect {
                        Toast.makeText(requireContext(), "${it.size}", Toast.LENGTH_SHORT).show()
                        binding.recycler.visible = true
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun setBindings(binding: FragmentMovieListBinding) {
        binding.recycler.endless { viewModel.getNextPage() }
        binding.recycler.adapter = adapter

        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.swipeToRefresh.setOnRefreshListener {
            viewModel.getNextPage(true)
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}