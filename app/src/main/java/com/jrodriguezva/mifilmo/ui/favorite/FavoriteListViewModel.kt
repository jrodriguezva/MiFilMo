package com.jrodriguezva.mifilmo.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.mifilmo.domain.model.Movie
import com.jrodriguezva.mifilmo.domain.usecase.DiscoverAllFavoriteMovies
import com.jrodriguezva.mifilmo.domain.usecase.GetAllFavoriteMovies
import com.jrodriguezva.mifilmo.domain.usecase.RemoveAllData
import com.jrodriguezva.mifilmo.domain.usecase.SaveFavoriteMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val discoverAllFavoriteMovies: DiscoverAllFavoriteMovies,
    private val getAllFavoriteMovies: GetAllFavoriteMovies,
    private val removeAllData: RemoveAllData,
    private val saveFavoriteMovie: SaveFavoriteMovie,
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    val movies: Flow<List<Movie>> get() = getAllFavoriteMovies()

    init {
        discoverFavoriteMovies()
    }

    private fun discoverFavoriteMovies() {
        viewModelScope.launch { discoverAllFavoriteMovies() }
    }

    fun onClickFavorite(movie: Movie) {
        viewModelScope.launch {
            saveFavoriteMovie(movie)
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            if (removeAllData()) {
                discoverFavoriteMovies()
            }
        }
    }
}