package com.toddy.casaportemporada.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.activity.FormAnuncioActivity
import com.toddy.casaportemporada.activity.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var tvCriarConta: TextView
    private lateinit var tvRecuperarConta: TextView
    private lateinit var etEmail: EditText
    private lateinit var etSenha: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponents()
        configClicks()
    }

    private fun configClicks() {
        tvCriarConta.setOnClickListener {
            startActivity(Intent(this, CriarContaActivity::class.java))
        }

        tvRecuperarConta.setOnClickListener {
            startActivity(Intent(this, RecuperarContaActivity::class.java))
        }
    }


    private fun initComponents() {
        progressBar = findViewById(R.id.progress_bar)
        etEmail = findViewById(R.id.et_email)
        etSenha = findViewById(R.id.et_senha)
        tvCriarConta = findViewById(R.id.tv_criar_conta)
        tvRecuperarConta = findViewById(R.id.tv_tv_recuperar_conta)
    }

    fun validaDados(view: View) {
        val email: String = etEmail.text.toString()
        val senha: String = etSenha.text.toString()

        when {
            email.isEmpty() -> {
                etEmail.requestFocus()
                etEmail.error = "Infome um email"
            }
            senha.isEmpty() -> {
                etSenha.requestFocus()
                etSenha.error = "Infome um email"
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
                startActivity(Intent(this, FormAnuncioActivity::class.java))
            } else {
                val error: String? = it.exception?.message
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}