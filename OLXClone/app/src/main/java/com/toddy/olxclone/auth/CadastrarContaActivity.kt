package com.toddy.olxclone.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.toddy.olxclone.R
import com.toddy.olxclone.model.User

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

                progressBar.visibility = View.VISIBLE

                val user = User()
                user.nome = nome
                user.email = email
                user.telefone = telefone
                user.senha = senha
                cadastrarUser(user)
            }
        }
    }

    private fun cadastrarUser(user: User) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(user.email, user.senha).addOnCompleteListener {
            if (it.isSuccessful) {
                val id: String = it.result.user!!.uid
                user.id = id
                user.salvar()
            } else {
                val error: String? = it.exception!!.message
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }

            progressBar.visibility = View.GONE
        }
    }
}