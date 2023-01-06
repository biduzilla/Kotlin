package com.toddy.gerenciadorreceitas.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.toddy.gerenciadorreceitas.R

class EsqueciSenhaActivity : AppCompatActivity() {
    private lateinit var edEmail: EditText
    private lateinit var ibVoltar: ImageButton
    private lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esqueci_senha)

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
        toolbarTitulo.text = "Recuperar Conta"

        edEmail = findViewById(R.id.et_email)
        ibVoltar = findViewById(R.id.iv_back)
        progressBar = findViewById(R.id.progress_bar)
    }

    fun validaDadosSenha() {
        val email: String = edEmail.text.toString()

        if (email.isNotEmpty()) {
            progressBar.visibility = View.GONE

            recuperarConta(email)
        } else {
            edEmail.requestFocus()
            edEmail.error = "Campo Obrigatório"
        }
    }

    private fun recuperarConta(email: String) {
        val auth = FirebaseAuth.getInstance()
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Email enviado com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                val error: String = it.exception!!.message.toString()
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
            progressBar.visibility = View.GONE
        }
    }
}