package com.example.armazenamentointerno

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var editNome: EditText? = null
    private val ARQUIVO_PREFERENCIA:String = "Arq"
    private var tvNome:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editNome = findViewById(R.id.ed_nome)
        tvNome = findViewById(R.id.tv_nome)

        recuperarDados()
    }

    fun salvarDados(view:View){
        val nome:String = editNome!!.text.toString()

        if (nome.isNotEmpty()) {
            val sharedPreferences: SharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,
                0)
            val editor:SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("meu_nome", nome)
            editor.apply()
        } else{
            editNome!!.error = "Informe seu nome"
        }
    }

    private fun recuperarDados(){
        val sharedPreferences: SharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,
            0)
        var nomeRecuperado: String? = sharedPreferences.getString("meu_nome", "")
        tvNome!!.text = nomeRecuperado
    }
}


