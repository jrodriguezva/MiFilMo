package com.jrodriguezva.mifilmo.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.model.People
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.usecase.GetMovie
import com.jrodriguezva.mifilmo.domain.usecase.GetPeoplesMovie
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getPeopleMovie: GetPeoplesMovie,
    getMovie: GetMovie,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val MOVIE_ID_SAVED_STATE_KEY = "movieId"
    }

    val movieId: Int = savedStateHandle.get<Int>(MOVIE_ID_SAVED_STATE_KEY) ?: 0
    val movie: Flow<Movie> = getMovie(movieId)


    private val _spinner = MutableStateFlow(false)
    val spinner: StateFlow<Boolean> = _spinner


    private val _peoples = MutableStateFlow<List<People>>(emptyList())
    val peoples: MutableStateFlow<List<People>> = _peoples

    init {
        viewModelScope.launch {
            getPeopleMovie(movieId).collect {
                when (it) {
                    is Resource.Success -> {
                        _spinner.value = false
                        _peoples.value = it.data
                    }
                    is Resource.Failure -> _spinner.value = false
                    Resource.Loading -> _spinner.value = true
                }
            }
        }
    }

}