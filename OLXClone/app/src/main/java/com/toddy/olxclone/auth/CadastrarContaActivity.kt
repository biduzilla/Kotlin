package com.toddy.olxclone.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.toddy.olxclone.R

class CadastrarContaActivity : AppCompatActivity() {
    private lateinit var edEmail: EditText
    private lateinit var edNome: EditText
    private lateinit var edSenha: EditText
    private lateinit var edTelefone: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_conta)

        initComponents()
    }

    private fun initComponents() {
        edEmail = findViewById(R.id.edit_email)
        edNome = findViewById(R.id.edit_nome)
        edSenha = findViewById(R.id.edit_senha)
        edTelefone = findViewById(R.id.edit_telefone)
        progressBar = findViewById(R.id.progress_bar)
    }

    fun validaDados(view: View) {
        val nome: String = edNome.text.toString()
        val email: String = edEmail.text.toString()
        val telefone: String = edTelefone.text.toString()
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
            telefone.isEmpty() -> {
                edTelefone.requestFocus()
                edTelefone.error = "Campo Obrigatório"
            }
            senha.isEmpty() -> {
                edSenha.requestFocus()
                edSenha.error = "Campo Obrigatório"
            }
            else -> {
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
            }
        }
    }
}