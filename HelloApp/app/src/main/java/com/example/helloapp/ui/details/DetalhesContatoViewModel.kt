package com.example.helloapp.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helloapp.utils.ID_CONTATO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetalhesContatoViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val idContato = savedStateHandle.get<Long>(ID_CONTATO)

    private val _uiState = MutableStateFlow(DetalhesContatoUiState())
    val uiState: StateFlow<DetalhesContatoUiState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            carregarContato()
        }
    }

    private suspend fun carregarContato(){

    }

    suspend fun removerContato(){

    }
}