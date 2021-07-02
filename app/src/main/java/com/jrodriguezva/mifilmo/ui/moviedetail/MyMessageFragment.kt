package com.jrodriguezva.mifilmo.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.FragmentMessageBinding
import com.jrodriguezva.mifilmo.ui.moviedetail.adapter.MessagesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyMessageFragment : Fragment(R.layout.fragment_message) {

    private var fragmentBinding: FragmentMessageBinding? = null
    private val adapter = MessagesAdapter()

    private val viewModel: MyMessageViewModel by viewModels()

    companion object {

        private const val MOVIE_ID_SAVED_STATE_KEY = "movieId"
        fun getInstance(movieId: Int) = MyMessageFragment().apply {
            val args = Bundle()
            args.putInt(MOVIE_ID_SAVED_STATE_KEY, movieId)
            arguments = args
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMessageBinding.bind(view)
        fragmentBinding = binding
        setBindings(binding)
        setObservers()
        arguments?.getInt(MOVIE_ID_SAVED_STATE_KEY)?.let { viewModel.loadMessages(it) }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.messages.collect {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun setBindings(binding: FragmentMessageBinding) {
        binding.recycler.adapter = adapter
    }


    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}