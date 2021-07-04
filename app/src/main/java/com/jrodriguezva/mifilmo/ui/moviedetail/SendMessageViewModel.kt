package com.jrodriguezva.mifilmo.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.mifilmo.domain.model.Message
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.usecase.PushMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SendMessageViewModel @Inject constructor(
    private val pushMessage: PushMessage,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        private const val MOVIE_ID_SAVED_STATE_KEY = "movieId"
    }

    val movieId: Int = savedStateHandle.get<Int>(MOVIE_ID_SAVED_STATE_KEY) ?: 0
    private val _navigate = MutableStateFlow(false)
    val navigate: StateFlow<Boolean> = _navigate

    fun sendMessage(text: String) {
        viewModelScope.launch {
            pushMessage(Message(text = text, date = Date(), movieId = movieId)).collect {
                when (it) {
                    is Resource.Success -> _navigate.value = true
                    else -> _navigate.value = false
                }
            }
        }
    }


}