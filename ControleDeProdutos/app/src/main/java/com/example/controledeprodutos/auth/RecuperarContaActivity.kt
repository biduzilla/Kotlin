package com.example.controledeprodutos.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.controledeprodutos.R
import com.example.controledeprodutos.helper.FireBaseHelper
import com.google.firebase.auth.FirebaseAuth

class RecuperarContaActivity : AppCompatActivity() {
    private var editEmail: EditText? = null
    private var progressBar: ProgressBar? = null
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_conta)

        initComponents()
    }

    private fun initComponents() {
        auth = FirebaseAuth.getInstance()

        editEmail = findViewById(R.id.edit_email)
        progressBar = findViewById(R.id.progressBar)
    }

    fun recuperarSenha(view: View) {
        val email: String = editEmail?.text.toString().trim()
        when {
            email.isEmpty() -> {
                editEmail?.requestFocus()
                editEmail?.error = "Informe seu Email"
            }
            else -> {
                progressBar?.visibility = View.VISIBLE
                enviarEmail(email)
            }
        }
    }

    private fun enviarEmail(email: String) {
        FireBaseHelper.auth.sendPasswordResetEmail(email).addOnCompleteListener(this) {
            when {
                it.isSuccessful -> {
                    Toast.makeText(this, "Email Enviado com Sucesso", Toast.LENGTH_LONG).show()
                }
                else -> {
                    val error: String = it.exception?.message.toString()
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
            }
            progressBar!!.visibility = View.GONE
        }
    }
}