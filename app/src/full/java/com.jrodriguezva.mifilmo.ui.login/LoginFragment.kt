package com.jrodriguezva.mifilmo.ui.login

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.jrodriguezva.mifilmo.BuildConfig
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.FragmentLoginBinding
import com.jrodriguezva.mifilmo.utils.extensions.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {


    private var googleSignInClient: GoogleSignInClient? = null

    companion object {
        private const val RANDOM_IMAGE_URL = "https://source.unsplash.com/random/480x800"
    }

    private val viewModel by viewModels<LoginViewModel>()
    private var fragmentBinding: FragmentLoginBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_API_KEY)
            .requestProfile()
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }


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

        context?.loadImage(RANDOM_IMAGE_URL) { drawable ->
            binding.container.background = drawable
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
                    viewModel.passError.collect {
                        binding.passwordLayout.error = it
                    }
                }
                launch {
                    viewModel.loading.collect {
                        if (!it) {
                            animation.end()
                            binding.container.transitionToEnd()
                        }
                    }
                }
                launch {
                    viewModel.logged.collect {
                        val direction = LoginFragmentDirections.actionLoginFragmentToMainActivity()
                        findNavController().navigate(direction)
                        requireActivity().finish()
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

        binding.googleSignIn.setOnClickListener {
            signIn()
        }

        binding.register.setOnClickListener { view ->
            val direction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            view.findNavController().navigate(direction)
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        googleSignInActivity.launch(signInIntent)
    }

    private val googleSignInActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                task.getResult(ApiException::class.java)?.run {
                    idToken?.let { token -> viewModel.signInWithGoogle(token) }
                }
            } catch (e: ApiException) {
            }
        }


}