package com.example.controledeprodutos.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.controledeprodutos.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var editEmail: EditText
    private lateinit var editSenha: EditText
    private lateinit var auth: FirebaseAuth

    private var tvCriarConta: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponets()
        configClicks()
    }

    fun logar(view: View) {
        val email: String = editEmail.text.toString().trim()
        val senha: String = editSenha.text.toString()

        if (email.isNotEmpty()) {
            if (senha.isNotEmpty()) {
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
            } else {
                editSenha.requestFocus()
                editSenha.error = "Digite sua Senha"
            }
        } else {
            editEmail.requestFocus()
            editEmail.error = "Digite seu Email"
        }
    }

    private fun configClicks() {
        tvCriarConta!!.setOnClickListener {
            startActivity(Intent(this, CriarContaActivity::class.java))
        }
    }

    private fun initComponets() {
        editEmail = findViewById(R.id.edit_email)
        editSenha = findViewById(R.id.edit_senha)
        tvCriarConta = findViewById(R.id.tv_criar_conta)
    }
}