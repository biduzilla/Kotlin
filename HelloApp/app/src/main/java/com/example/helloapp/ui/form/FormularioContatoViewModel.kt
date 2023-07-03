package com.example.helloapp.ui.form

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.helloapp.extensions.converterParaDate
import com.example.helloapp.extensions.converterParaString
import com.example.helloapp.utils.ID_CONTATO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FormularioContatoViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val idContato = savedStateHandle.get<Long>(ID_CONTATO)

    private val _uiState = MutableStateFlow(FormularioContatoUiState())
    val uiState: StateFlow<FormularioContatoUiState>
        get() = _uiState.asStateFlow()

    init {
        _uiState.update { state ->
            state.copy(
                onNomeMudou = {
                    _uiState.value = _uiState.value.copy(
                        nome = it
                    )
                },
                onSobrenomeMudou = {
                    _uiState.value = _uiState.value.copy(
                        sobrenome = it
                    )
                },
                onTelefoneMudou = {
                    _uiState.value = _uiState.value.copy(
                        telefone = it
                    )
                },
                onFotoPerfilMudou = {
                    _uiState.value = _uiState.value.copy(
                        fotoPerfil = it
                    )
                },
                onAniversarioMudou = {
                    _uiState.value = _uiState.value.copy(
                        aniversario = it.converterParaDate(), mostrarCaixaDialogoData = false
                    )
                },
                onMostrarCaixaDialogoImagem = {
                    _uiState.value = _uiState.value.copy(
                        mostrarCaixaDialogoImagem = it
                    )
                }, onMostrarCaixaDialogoData = {
                    _uiState.value = _uiState.value.copy(
                        mostrarCaixaDialogoData = it
                    )
                }
            )
        }
    }

    fun defineTextoAniversario(textoAniversario: String) {
        val textoAniversairo = _uiState.value.aniversario?.converterParaString() ?: textoAniversario

        _uiState.update {
            it.copy(textoAniversairo = textoAniversairo)
        }
    }

    fun carregaImagem(url: String) {
        _uiState.value = _uiState.value.copy(
            fotoPerfil = url,
            mostrarCaixaDialogoImagem = false
        )
    }
}