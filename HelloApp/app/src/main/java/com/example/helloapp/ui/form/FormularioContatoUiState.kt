package com.example.helloapp.ui.form

import java.util.*
import androidx.annotation.StringRes
import com.example.helloapp.R

data class FormularioContatoUiState(
    val id: Long = 0L,
    val nome: String = "",
    val sobrenome: String = "",
    val telefone: String = "",
    val fotoPerfil: String = "",
    val aniversario: Date? = null,
    val mostrarCaixaDialogoImagem: Boolean = false,
    val mostrarCaixaDialogoData: Boolean = false,
    val onNomeMudou: (String) -> Unit = {},
    val onSobrenomeMudou: (String) -> Unit = {},
    val onTelefoneMudou: (String) -> Unit = {},
    val onFotoPerfilMudou: (String) -> Unit = {},
    val onAniversarioMudou: (String) -> Unit = {},
    val onMostrarCaixaDialogoImagem: (mostrar: Boolean) -> Unit = {},
    val onMostrarCaixaDialogoData: (mostrar: Boolean) -> Unit = {},
    val textoAniversairo: String = "",
    @StringRes val tituloAppbar: Int? = R.string.titulo_cadastro_contato,
)
