package com.jrodriguezva.mifilmo.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.mifilmo.domain.model.Message
import com.jrodriguezva.mifilmo.domain.usecase.PushMessage
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun sendMessage(text: String) {
        viewModelScope.launch {
            pushMessage(
                Message(text = text, date = Date(), movieId = movieId)
            )
        }
    }


}