package com.toddy.casaportemporada.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.activity.auth.LoginActivity
import com.toddy.casaportemporada.model.Usuario

class MinhaContaActivity : AppCompatActivity() {

    private lateinit var edNome: EditText
    private lateinit var edTelefone: EditText
    private lateinit var edEmail: EditText
    private lateinit var ibVoltar: ImageButton
    private lateinit var ibSalvar: ImageButton
    private lateinit var progressBar: ProgressBar
    private var user = Usuario()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minha_conta)

        initComponents()
        recuperaDados()
        configClicks()
    }

    private fun configClicks() {
        ibVoltar.setOnClickListener { finish() }
        ibSalvar.setOnClickListener { validaDados() }
        val logout:Button =  findViewById(R.id.btn_sair)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun validaDados() {
        val nome: String = edNome.text.toString()
        val telefone: String = edTelefone.text.toString()

        when {
            nome.isEmpty() -> {
                edNome.requestFocus()
                edNome.error = "Informe seu nome"
            }
            telefone.isEmpty() -> {
                edTelefone.requestFocus()
                edTelefone.error = "Informe seu telefone"
            }
            else -> {
                progressBar.visibility = View.VISIBLE

                user.nome = nome
                user.telefone = telefone
                user.salvar()

                progressBar.visibility = View.GONE

                Toast.makeText(this, "Informações atualizadas", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun recuperaDados() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("usuario")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(Usuario::class.java)!!

                configDados()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun configDados() {
        edNome.setText(user!!.nome)
        edTelefone.setText(user!!.telefone)
        edEmail.setText(user!!.email)

        progressBar.visibility = View.GONE
    }


    private fun initComponents() {
        val tvTitulo: TextView = findViewById(R.id.tv_titulo_toolbar_voltar)
        tvTitulo.text = "Alterar Dados"

        edEmail = findViewById(R.id.et_email)
        edTelefone = findViewById(R.id.et_telefone)
        edNome = findViewById(R.id.et_nome)
        ibVoltar = findViewById(R.id.ib_toolbar_voltar)
        ibSalvar = findViewById(R.id.ib_toolbar_salvar)
        progressBar = findViewById(R.id.progress_bar)
    }
}