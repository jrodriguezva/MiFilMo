package com.jrodriguezva.mifilmo.ui.person

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jrodriguezva.mifilmo.domain.model.Person
import com.jrodriguezva.mifilmo.domain.model.Resource
import com.jrodriguezva.mifilmo.domain.usecase.GetPerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val getPerson: GetPerson,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val PERSON_ID_SAVED_STATE_KEY = "personId"
    }

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> get() = _loading

    private val _person = MutableSharedFlow<Person>()
    val person: SharedFlow<Person> = _person

    val personId: Int = savedStateHandle.get<Int>(PERSON_ID_SAVED_STATE_KEY) ?: 0

    fun loadPerson() {
        viewModelScope.launch {
            getPerson(personId).collect {
                when (it) {
                    is Resource.Loading -> _loading.value = true
                    is Resource.Success -> {
                        _person.emit(it.data)
                        _loading.value = false
                    }
                    else -> {
                        _loading.value = false
                    }
                }
            }
        }
    }

}
