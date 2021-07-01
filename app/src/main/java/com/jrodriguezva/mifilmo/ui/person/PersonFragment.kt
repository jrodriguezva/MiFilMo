package com.jrodriguezva.mifilmo.ui.person

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.jrodriguezva.mifilmo.R
import com.jrodriguezva.mifilmo.databinding.FragmentPersonBinding
import com.jrodriguezva.mifilmo.domain.model.Person
import com.jrodriguezva.mifilmo.ui.main.MainActivity
import com.jrodriguezva.mifilmo.utils.extensions.formatToBirthday
import com.jrodriguezva.mifilmo.utils.extensions.loadProfile
import com.jrodriguezva.mifilmo.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonFragment : Fragment(R.layout.fragment_person) {
    companion object {
        private const val EMPTY_FIELD = "-"
    }

    private val viewModel: PersonViewModel by viewModels()
    private var fragmentBinding: FragmentPersonBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FragmentPersonBinding.bind(view)
        setBindings()
        setObservers()
        viewModel.loadPerson()
    }

    private fun setObservers() {
        fragmentBinding?.run {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.person.collect {
                            setPersonDetail(it)
                        }
                    }
                    launch {
                        viewModel.loading.collect {
                            progress.visible = it
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
        }
    }

    private fun FragmentPersonBinding.setPersonDetail(it: Person) {
        profileCard.visible = true
        toolbarLayout.title = it.name
        toolbar.title = it.name
        image.loadProfile(it.profilePath.orEmpty())
        name.text = it.name
        biographyCard.visible = !it.biography.isNullOrEmpty()
        biography.text = it.biography

        birthday.text = it.birthday?.formatToBirthday() ?: EMPTY_FIELD
        placeOfBirth.text = it.placeOfBirth ?: EMPTY_FIELD
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
