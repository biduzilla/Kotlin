package com.toddy.casaportemporada.activity.auth

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

class RecuperarContaActivity : AppCompatActivity() {
    private lateinit var tvTituloToolBar: TextView
    private lateinit var etEmail: EditText
    private lateinit var ibVoltar: ImageButton
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_conta)

        initComponets()
        configClicks()
    }

    private fun initComponets() {
        tvTituloToolBar = findViewById(R.id.tv_titulo_toolbar_voltar)
        tvTituloToolBar.text = "Recuperar Conta"
        etEmail = findViewById(R.id.et_email_recuperar)
        progressBar = findViewById(R.id.progress_bar)
        ibVoltar = findViewById(R.id.ib_toolbar_voltar)
    }

    fun validaDados(view: View) {
        val email: String = etEmail.text.toString()

        if (email.isNotEmpty()) {
            recuperarConta(email)
            progressBar.visibility = View.VISIBLE
        } else {
            etEmail.requestFocus()
            etEmail.error = "Informe um email"
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

    private fun configClicks() {
        ibVoltar.setOnClickListener {
            finish()
        }
    }
}