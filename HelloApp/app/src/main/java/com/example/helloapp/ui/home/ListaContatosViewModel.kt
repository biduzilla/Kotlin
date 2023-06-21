package com.example.helloapp.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListaContatosViewModel : ViewModel() {

    private val _uistate = MutableStateFlow(ListaContatosUiState())
    val uiState: StateFlow<ListaContatosUiState> get() = _uistate.asStateFlow()
}