package com.jrodriguezva.mifilmo.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.FragmentProfileBinding
import com.jrodriguezva.mifilmo.domain.model.User
import com.jrodriguezva.mifilmo.ui.login.LoginActivity
import com.jrodriguezva.mifilmo.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()
    private var fragmentBinding: FragmentProfileBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentProfileBinding.bind(view)
        setBindings()
        setObservers()
    }

    private fun setObservers() {
        fragmentBinding?.run {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.user.collect {
                            setCharacterDetail(it)
                        }
                    }
                    launch {
                        viewModel.deletedUser.collect {
                            if (it) {
                                restartApp()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setBindings() {
        fragmentBinding?.run {
            toolbarLayout.title = ""
            val navController = findNavController()
            toolbar.setupWithNavController(navController)
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.logout -> {
                        viewModel.logoutAccount()
                        restartApp()
                        true
                    }
                    else -> false
                }
            }
            deleteAccountButton.setOnClickListener { showAlert() }
        }
    }

    private fun restartApp() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun FragmentProfileBinding.setCharacterDetail(it: User) {
        toolbarLayout.title = it.name
        image.apply {
            load(it.photoUrl)
        }
        name.text = it.name
        status.text = it.providerId
        email.text = it.email
        password.text = "********"
    }

    private fun showAlert() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(resources.getString(R.string.delete_account))
            .setMessage(resources.getString(R.string.delete_account_info))
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                viewModel.deleteAccount()
            }
            .show()
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        (activity as MainActivity).visibilityBottomNavigation(false)
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).visibilityBottomNavigation(true)
    }
}
