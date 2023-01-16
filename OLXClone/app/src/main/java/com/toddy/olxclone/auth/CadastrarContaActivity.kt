package com.toddy.olxclone.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.toddy.olxclone.activitys.MainActivity
import com.toddy.olxclone.R
import com.toddy.olxclone.model.User
import com.toddy.olxclone.utils.FirebaseHelper

class CadastrarContaActivity : AppCompatActivity() {
    private lateinit var edEmail: EditText
    private lateinit var edNome: EditText
    private lateinit var edSenha: EditText
    private lateinit var edTelefone: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var txtToolbar: TextView
    private lateinit var ibVoltar: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastrar_conta)

        initComponents()
        configClicks()
    }

    private fun initComponents() {
        edEmail = findViewById(R.id.edit_email)
        edNome = findViewById(R.id.edit_nome)
        edSenha = findViewById(R.id.edit_senha)
        edTelefone = findViewById(R.id.edit_telefone)
        progressBar = findViewById(R.id.progress_bar)
        ibVoltar = findViewById(R.id.ib_voltar)
        txtToolbar = findViewById(R.id.text_toolbar)
        txtToolbar.text = "Cadastrar conta"
    }

    private fun configClicks(){
        ibVoltar.setOnClickListener {
            finish()
        }
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
                user.salvar(progressBar,this)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                val error: String = FirebaseHelper.validaErros(it.exception?.message!!)
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }

        }
    }
}