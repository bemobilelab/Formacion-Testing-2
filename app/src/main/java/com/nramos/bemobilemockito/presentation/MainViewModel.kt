package com.nramos.bemobilemockito.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nramos.bemobilemockito.data.di.IoDispatcher
import com.nramos.bemobilemockito.domain.model.Character
import com.nramos.bemobilemockito.domain.onFailure
import com.nramos.bemobilemockito.domain.onSuccess
import com.nramos.bemobilemockito.domain.usecases.GetDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDataUseCase: GetDataUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state.asStateFlow()

    fun getData() {
        viewModelScope.launch {

            val result = getDataUseCase()

            result.onSuccess { characters ->
                _state.update {
                    it.copy(
                        loading = false,
                        characters = characters
                    )
                }
            }.onFailure {
                _state.update {
                    it.copy(
                        loading = false,
                        error = true
                    )
                }
            }
        }
    }

    data class State(
        var loading: Boolean = true,
        var characters: List<Character> = emptyList(),
        var error: Boolean = false
    )

}