package com.toddy.olxclone.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.toddy.olxclone.R

class CategoriasActivity : AppCompatActivity() {
    private lateinit var ibVoltar:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        initComponents()
        configClicks()
    }

    private fun configClicks(){
        ibVoltar.setOnClickListener {
            finish()
        }
    }

    private fun initComponents(){
        val titulo:TextView = findViewById(R.id.text_toolbar)
        titulo.text = "Categorias"

        ibVoltar = findViewById(R.id.ib_voltar)
    }
}