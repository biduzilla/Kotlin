package com.toddy.casaportemporada.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.toddy.casaportemporada.R

class LoginActivity : AppCompatActivity() {
    private lateinit var tvCriarConta: TextView
    private lateinit var tvRecuperarConta: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponents()
        configClicks()
    }

    private fun configClicks() {
        tvCriarConta.setOnClickListener {
            startActivity(Intent(this, CriarContaActivity::class.java))
        }
    }

    private fun initComponents() {
        tvCriarConta = findViewById(R.id.tv_criar_conta)
        tvRecuperarConta = findViewById(R.id.tv_tv_recuperar_conta)
    }
}