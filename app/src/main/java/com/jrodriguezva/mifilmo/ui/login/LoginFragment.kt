package com.jrodriguezva.mifilmo.ui.login

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel by activityViewModels<LoginViewModel>()
    private var fragmentBinding: FragmentLoginBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        fragmentBinding = binding
        setBindings(binding)
        setObservers(binding)
    }

    private fun setObservers(binding: FragmentLoginBinding) {
        val animation = ObjectAnimator.ofPropertyValuesHolder(
            binding.logo,
            PropertyValuesHolder.ofFloat("scaleX", 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1.2f),
        ).apply {
            duration = 500
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            start()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.emailError.collect {
                        binding.usernameLayout.error = it
                    }
                }
                launch {
                    viewModel.loginError.collect {
                        binding.usernameLayout.error = it
                    }
                }
                launch {
                    viewModel.passError.flowWithLifecycle(
                        viewLifecycleOwner.lifecycle,
                        Lifecycle.State.STARTED
                    ).collect {
                        binding.passwordLayout.error = it
                    }
                }
                launch {
                    viewModel.loading.flowWithLifecycle(
                        viewLifecycleOwner.lifecycle,
                        Lifecycle.State.STARTED
                    ).collect {
                        if (!it) {
                            animation.end()
                            binding.container.transitionToEnd()
                        }
                    }
                }
            }
        }
    }


    private fun setBindings(binding: FragmentLoginBinding) {
        binding.login.setOnClickListener {
            viewModel.signIn(
                binding.usernameEdit.text.toString(),
                binding.passwordEdit.text.toString()
            )
        }
    }


}