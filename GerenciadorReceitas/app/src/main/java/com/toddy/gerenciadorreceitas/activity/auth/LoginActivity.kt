package com.toddy.gerenciadorreceitas.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.toddy.gerenciadorreceitas.R
import com.toddy.gerenciadorreceitas.activity.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var edEmail: EditText
    private lateinit var edSenha: EditText
    private lateinit var tvCadastrar: TextView
    private lateinit var tvRecuperar: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponets()
        configClicks()
    }

    private fun configClicks() {
        tvCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastrarActivity::class.java))
        }
        tvRecuperar.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    EsqueciSenhaActivity::class.java
                )
            )
        }
    }

    private fun initComponets() {
        edEmail = findViewById(R.id.et_email)
        edSenha = findViewById(R.id.et_Senha)
        tvCadastrar = findViewById(R.id.tv_cadastrar)
        tvRecuperar = findViewById(R.id.tv_recuperar)
        progressBar = findViewById(R.id.progress_bar)
    }

    fun validaDados(view: View) {
        val email: String = edEmail.text.toString()
        val senha: String = edSenha.text.toString()

        when {
            email.isEmpty() -> {
                edEmail.requestFocus()
                edEmail.error = "Informe seu email"
            }
            senha.isEmpty() -> {
                edSenha.requestFocus()
                edSenha.error = "Informe sua senha"
            }
            else -> {
                progressBar.visibility = View.VISIBLE
                logar(email, senha)
            }
        }
    }

    private fun logar(email: String, senha: String) {
        val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener {
            if (it.isSuccessful) {
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val error: String? = it.exception!!.message
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()

            }
            progressBar.visibility = View.GONE
        }
    }
}