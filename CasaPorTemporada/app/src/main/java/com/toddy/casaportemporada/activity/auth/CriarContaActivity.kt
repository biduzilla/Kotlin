package com.toddy.casaportemporada.activity.auth

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.activity.MainActivity
import com.toddy.casaportemporada.model.Usuario

class CriarContaActivity : AppCompatActivity() {
    private lateinit var ibVoltar: ImageButton
    private lateinit var tvTituloToolBar: TextView
    private lateinit var etNome: EditText
    private lateinit var etEmail: EditText
    private lateinit var etTelefone: EditText
    private lateinit var etSenha: EditText
    private lateinit var progressBar: ProgressBar
    private var usuario = Usuario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        initComponent()
        configClick()
    }

    fun validaDados(view: View) {
        val nome: String = etNome.text.toString()
        val email: String = etEmail.text.toString()
        val telefone: String = etTelefone.text.toString()
        val senha: String = etSenha.text.toString()

        when {
            nome.isEmpty() -> {
                etNome.requestFocus()
                etNome.error = "Informe seu nome"
            }
            email.isEmpty() -> {
                etEmail.requestFocus()
                etEmail.error = "Informe seu nome"
            }
            telefone.isEmpty() -> {
                etTelefone.requestFocus()
                etTelefone.error = "Informe seu telefone"
            }
            senha.isEmpty() -> {
                etSenha.requestFocus()
                etSenha.error = "Informe sua senha"
            }
            else -> {

                progressBar.visibility = View.VISIBLE
                usuario.nome = nome
                usuario.telefone = telefone
                usuario.email = email
                usuario.senha = senha

                cadastrarUser(email, senha)

            }
        }
    }

    private fun cadastrarUser(email: String, senha: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener {
            if (it.isSuccessful) {
                val iduser: String = it.result.user!!.uid
                usuario.id = iduser
                usuario.salvar()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val error: String = it.exception!!.message.toString()
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initComponent() {
        ibVoltar = findViewById(R.id.ib_toolbar_voltar)
        etNome = findViewById(R.id.et_nome)
        etEmail = findViewById(R.id.et_email)
        etTelefone = findViewById(R.id.et_telefone)
        etSenha = findViewById(R.id.et_senha)
        progressBar = findViewById(R.id.progress_bar)
    }

    private fun configClick() {
        tvTituloToolBar = findViewById(R.id.tv_titulo_toolbar_voltar)
        tvTituloToolBar.text = "Criar Conta"

        ibVoltar.setOnClickListener {
            finish()
        }
    }

}