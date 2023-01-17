package com.toddy.olxclone.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.toddy.olxclone.R

class FormActivity : AppCompatActivity() {
    private lateinit var tituloToolbar:TextView
    private lateinit var ibVoltar:ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initComponents()
    }
    private fun initComponents(){
        tituloToolbar = findViewById(R.id.text_toolbar)
        tituloToolbar.text = "Novo Anúncio"

        ibVoltar = findViewById(R.id.ib_voltar)
    }

    private fun configClicks(){
        ibVoltar.setOnClickListener {
            finish()
        }
    }
}