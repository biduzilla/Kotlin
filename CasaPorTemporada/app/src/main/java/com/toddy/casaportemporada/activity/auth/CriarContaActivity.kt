package com.toddy.casaportemporada.activity.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.toddy.casaportemporada.R

class CriarContaActivity : AppCompatActivity() {
    private lateinit var ibVoltar: ImageButton
    private lateinit var tvTituloToolBar: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_conta)

        initComponent()
        configClick()
    }

    private fun initComponent() {
        ibVoltar = findViewById(R.id.ib_toolbar_voltar)

    }

    private fun configClick() {
        tvTituloToolBar = findViewById(R.id.tv_titulo_toolbar_voltar)
        tvTituloToolBar.text = "Criar Conta"

        ibVoltar.setOnClickListener {
            finish()
        }
    }

}