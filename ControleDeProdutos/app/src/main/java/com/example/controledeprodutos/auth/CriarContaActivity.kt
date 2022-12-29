package com.example.controledeprodutos.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.controledeprodutos.R
import com.example.controledeprodutos.activity.MainActivity
import com.example.controledeprodutos.helper.FireBaseHelper
import com.example.controledeprodutos.models.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class CriarContaActivity : AppCompatActivity() {
    private var editNome: TextView? = null
    private var editEmail: TextView? = null
    private var editSenha: TextView? = null
    private var progressBar: ProgressBar? = null
    private var ibVoltar: ImageButton? = null
    private var tvTitulo: TextView? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)
        auth = FirebaseAuth.getInstance()
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
        val nome = editNome!!.text.toString()
        val email = editEmail!!.text.toString()
        val senha = editSenha!!.text.toString()

        if (nome.isNotEmpty()) {
            if (email.isNotEmpty()) {
                if (senha.isNotEmpty()) {
                    progressBar?.visibility = View.VISIBLE
                    val usuario = Usuario()
                    usuario.nome = nome
                    usuario.senha = senha
                    usuario.email = email

                    salvarCadastro(usuario)

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

    private fun salvarCadastro(usuario: Usuario) {
        auth.createUserWithEmailAndPassword(usuario.email, usuario.senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val id: String? = auth.currentUser?.uid
                    usuario.id = id.toString()
                    finish()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
    }
}