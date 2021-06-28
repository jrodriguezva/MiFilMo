package com.jrodriguezva.mifilmo.ui.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.usecase.DiscoverMoreMovies
import com.jrodriguezva.mifilmo.domain.usecase.GetAllMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val discoverResultUseCase: DiscoverMoreMovies,
    private val getAllMovies: GetAllMovies,

    ) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    val movies: Flow<List<Movie>> get() = getAllMovies()

    init {
        getNextPage(true)
    }

    fun getNextPage(fromInit: Boolean = false) {
        viewModelScope.launch {
            discoverResultUseCase(fromInit).collect {
                when (it) {
                    is Resource.Success -> {
                        _loading.value = false
                    }
                    is Resource.Failure -> _loading.value = false
                    Resource.Loading -> _loading.value = true
                }
            }
        }
    }

}