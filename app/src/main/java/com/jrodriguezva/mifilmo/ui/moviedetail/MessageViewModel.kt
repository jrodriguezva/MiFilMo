package com.jrodriguezva.mifilmo.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.mifilmo.domain.model.Message
import com.jrodriguezva.mifilmo.domain.usecase.GetMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val getMessages: GetMessages,
) : ViewModel() {


    private val _messages = MutableSharedFlow<List<Message>>()
    val messages: SharedFlow<List<Message>> = _messages

    fun loadMessages(movieId: Int) {
        viewModelScope.launch {
            getMessages(movieId).collect {
                _messages.emit(it)
            }
        }
    }
}