package com.toddy.casaportemporada.activity.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.toddy.casaportemporada.R

class RecuperarContaActivity : AppCompatActivity() {
    private lateinit var tvTituloToolBar: TextView
    private lateinit var ibVoltar: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_conta)

        initComponets()
        configClicks()
    }

    private fun initComponets() {
        tvTituloToolBar = findViewById(R.id.tv_titulo_toolbar_voltar)
        tvTituloToolBar.text = "Recuperar Conta"

        ibVoltar = findViewById(R.id.ib_toolbar_voltar)
    }

    private fun configClicks() {
        ibVoltar.setOnClickListener {
            finish()
        }
    }

}