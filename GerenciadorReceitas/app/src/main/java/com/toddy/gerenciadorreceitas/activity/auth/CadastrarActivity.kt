package com.toddy.gerenciadorreceitas.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.toddy.gerenciadorreceitas.R
import com.toddy.gerenciadorreceitas.activity.MainActivity
import com.toddy.gerenciadorreceitas.models.Usuario

class CadastrarActivity : AppCompatActivity() {
    private lateinit var edEmail: EditText
    private lateinit var edNome: EditText
    private lateinit var edSenha: EditText
    private lateinit var ibVoltar: ImageButton
    private lateinit var progressBar: ProgressBar
    private var usuario = Usuario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar)

        initComponets()
        configClicks()
    }

    private fun configClicks() {
        ibVoltar.setOnClickListener {
            finish()
        }
    }

    private fun initComponets() {
        val toolbarTitulo: TextView = findViewById(R.id.toolbar_txt)
        toolbarTitulo.text = "Criar Conta"
        edEmail = findViewById(R.id.et_email)
        edSenha = findViewById(R.id.et_Senha)
        edNome = findViewById(R.id.et_nome)
        ibVoltar = findViewById(R.id.iv_back)
        progressBar = findViewById(R.id.progress_bar)
    }

    fun validaDados(view: View) {
        val nome: String = edNome.text.toString()
        val email: String = edEmail.text.toString()
        val senha: String = edSenha.text.toString()

        when {
            nome.isEmpty() -> {
                edNome.requestFocus()
                edNome.error = "Campo Obrigatório"
            }
            email.isEmpty() -> {
                edEmail.requestFocus()
                edEmail.error = "Campo Obrigatório"
            }
            senha.isEmpty() -> {
                edSenha.requestFocus()
                edSenha.error = "Campo Obrigatório"
            }
            else -> {
                progressBar.visibility = View.VISIBLE

                usuario.email = email
                usuario.senha = senha
                usuario.nome = nome

                cadastrarUser(email, senha)

            }
        }
    }

    private fun cadastrarUser(email: String, senha: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener {
            if (it.isSuccessful) {
                usuario.id = it.result.user!!.uid
                usuario.salvar()
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val error: String = it.exception!!.message.toString()
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}