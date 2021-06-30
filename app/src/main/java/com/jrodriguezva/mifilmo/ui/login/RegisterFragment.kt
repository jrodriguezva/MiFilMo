package com.jrodriguezva.mifilmo.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.FragmentRegisterBinding
import com.jrodriguezva.mifilmo.utils.extensions.visible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel by activityViewModels<RegisterViewModel>()
    private var fragmentBinding: FragmentRegisterBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRegisterBinding.bind(view)
        fragmentBinding = binding
        setBindings(binding)
        setObservers(binding)
    }

    private fun setObservers(binding: FragmentRegisterBinding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.emailError.collect { binding.emailLayout.error = it }
                }
                launch {
                    viewModel.nameError.collect { binding.nameLayout.error = it }
                }
                launch {
                    viewModel.passError.collect { binding.passwordLayout.error = it }
                }
                launch {
                    viewModel.registerError.collect { binding.emailLayout.error = it }
                }
                launch {
                    viewModel.loading.collect { binding.loading.visible = it }
                }
                launch {
                    viewModel.registered.collect {
                        val direction = LoginFragmentDirections.actionLoginFragmentToMainActivity()
                        findNavController().navigate(direction)
                        requireActivity().finish()
                    }
                }
            }
        }
    }


    private fun setBindings(binding: FragmentRegisterBinding) {
        binding.register.setOnClickListener {
            viewModel.signUp(
                binding.emailEdit.text.toString(),
                binding.passwordEdit.text.toString(),
                binding.nameEdit.text.toString()
            )
        }


        val startForProfileImageResult = createResultLauncher()
        binding.userPhoto.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(maxSize = 1024)
                .maxResultSize(width = 1080, height = 1080)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
    }

    private fun createResultLauncher() =
        registerForActivityResult(StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> data?.data?.let {
                    viewModel.setProfileImage(it.toString())
                    fragmentBinding?.userPhoto?.setImageURI(it)
                }

                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        ImagePicker.getError(data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }
}