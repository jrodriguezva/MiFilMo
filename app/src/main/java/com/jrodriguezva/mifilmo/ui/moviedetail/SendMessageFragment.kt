package com.jrodriguezva.mifilmo.ui.moviedetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.FragmentSendMessageBinding
import com.jrodriguezva.mifilmo.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendMessageFragment : Fragment(R.layout.fragment_send_message) {

    private var fragmentBinding: FragmentSendMessageBinding? = null
    private val args by navArgs<SendMessageFragmentArgs>()

    private val viewModel: SendMessageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSendMessageBinding.bind(view)
        fragmentBinding = binding
        setBindings(binding)
    }


    private fun setBindings(binding: FragmentSendMessageBinding) {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.send -> {
                    val text = binding.message.text.toString()
                    if (text.isEmpty()) {
                        binding.textField.error = getString(R.string.required)
                    } else {
                        viewModel.sendMessage(text)
                        findNavController().navigateUp()
                    }
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


    override fun onPause() {
        super.onPause()
        (activity as MainActivity).visibilityBottomNavigation(true)
    }

    override fun onResume() {
        (activity as MainActivity).visibilityBottomNavigation(false)
        super.onResume()
    }
}