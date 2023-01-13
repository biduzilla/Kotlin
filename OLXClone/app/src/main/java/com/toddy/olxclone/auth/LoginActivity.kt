package com.toddy.olxclone.auth

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
import com.toddy.olxclone.MainActivity
import com.toddy.olxclone.R
import com.toddy.olxclone.utils.FirebaseHelper

class LoginActivity : AppCompatActivity() {
    private lateinit var edEmail: EditText
    private lateinit var edSenha: EditText
    private lateinit var tvCadastrar: TextView
    private lateinit var tvRecuperar: TextView
    private lateinit var txtToolbar: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var ibVoltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponets()
        configClicks()
    }

    private fun initComponets() {
        edEmail = findViewById(R.id.edit_email)
        edSenha = findViewById(R.id.edit_senha)
        ibVoltar = findViewById(R.id.ib_voltar)
        ibVoltar.visibility = View.GONE
        progressBar = findViewById(R.id.progress_bar)
        tvCadastrar = findViewById(R.id.tv_cadastrar)
        tvRecuperar = findViewById(R.id.tv_esqueci_senha)
        tvRecuperar = findViewById(R.id.tv_esqueci_senha)
        txtToolbar = findViewById(R.id.text_toolbar)
        txtToolbar.text = "Entrar na sua conta"
    }

    private fun configClicks() {
        tvCadastrar.setOnClickListener {
            startActivity(Intent(this, CadastrarContaActivity::class.java))
        }
        tvRecuperar.setOnClickListener {
            startActivity(Intent(this, RecuperarSenhaActivity::class.java))
        }
    }

    fun validaDados(view: View) {
        val email: String = edEmail.text.toString()
        val senha: String = edSenha.text.toString()

        when {
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
                login(email,senha)
            }
        }
    }

    private fun login(email: String, senha: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    val error: String = FirebaseHelper.validaErros(it.exception?.message!!)
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.GONE
            }
    }
}