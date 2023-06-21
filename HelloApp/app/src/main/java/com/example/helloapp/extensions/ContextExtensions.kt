package com.example.helloapp.extensions

import android.content.Context
import android.widget.Toast

fun Context.mostrarMensagem(
    texto: String,
    duracao: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(
        this,
        texto,
        duracao,
    ).show()
}