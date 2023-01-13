package com.toddy.olxclone.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.toddy.olxclone.R

class RecuperarSenhaActivity : AppCompatActivity() {
    private lateinit var edEmail: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var tituloToolbar: TextView
    private lateinit var ibVoltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_senha)

        initComponents()
        configClicks()
    }

    private fun initComponents() {
        progressBar = findViewById(R.id.progress_bar)
        tituloToolbar = findViewById(R.id.text_toolbar)
        tituloToolbar.text = "Recuperar Senha"
        ibVoltar = findViewById(R.id.ib_voltar)
        edEmail = findViewById(R.id.edit_email)
    }

    private fun configClicks(){
        ibVoltar.setOnClickListener {
            finish()
        }
    }

    fun validaDados(view: View) {
        val email: String = edEmail.text.toString()

        if (email.isNotEmpty()) {
            recuperarSenha(email)
        } else {
            progressBar.visibility = View.VISIBLE
            edEmail.requestFocus()
            edEmail.error = "Campo Obrigatório"
        }
    }

    private fun recuperarSenha(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    this,
                    "Verifique a caixa de entrada do seu email",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, it.exception!!.message, Toast.LENGTH_SHORT).show()
            }
            progressBar.visibility = View.GONE
        }
    }
}