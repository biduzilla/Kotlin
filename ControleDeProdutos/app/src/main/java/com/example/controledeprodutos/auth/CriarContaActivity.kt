package com.example.controledeprodutos.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.controledeprodutos.R

class CriarContaActivity : AppCompatActivity() {
    private var editNome: TextView? = null
    private var editEmail: TextView? = null
    private var editSenha: TextView? = null
    private var progressBar: ProgressBar? = null
    private var ibVoltar: ImageButton? = null
    private var tvTitulo:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        iniciaComponentes()
        configClicks()
    }

    private fun configClicks() {
        ibVoltar?.setOnClickListener {
                finish()
            }
    }

    private fun iniciaComponentes() {
        tvTitulo = findViewById(R.id.tv_titulo_toolbar)
        editNome = findViewById(R.id.edit_nome)
        editEmail = findViewById(R.id.edit_email)
        editSenha = findViewById(R.id.edit_senha)
        progressBar = findViewById(R.id.progressBar)
        ibVoltar = findViewById(R.id.ib_voltar)

        tvTitulo?.text = "Criar Nova Conta"
    }

    fun validaDados(view: View) {
        val nome = editNome!!.text
        val email = editEmail!!.text
        val senha = editSenha!!.text

        if (nome.isNotEmpty()) {
            if (email.isNotEmpty()) {
                if (senha.isNotEmpty()) {
                    Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
                } else {
                    editSenha!!.requestFocus()
                    editSenha!!.error = "Informe sua Senha"
                }
            } else {
                editEmail!!.requestFocus()
                editEmail!!.error = "Informe seu Email"
            }
        } else {
            editNome!!.requestFocus()
            editNome!!.error = "Informe seu Nome"
        }
    }
}