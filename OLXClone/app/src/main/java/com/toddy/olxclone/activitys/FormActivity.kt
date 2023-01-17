package com.toddy.olxclone.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.blackcat.currencyedittext.CurrencyEditText
import com.toddy.olxclone.R
import java.util.*

class FormActivity : AppCompatActivity() {
    private lateinit var tituloToolbar: TextView
    private lateinit var ibVoltar: ImageButton
    private lateinit var etPreco: CurrencyEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initComponents()
        configClicks()
    }

    fun selecionarCategoria(view: View){
        startActivity(Intent(this, CategoriasActivity::class.java))
    }

    private fun initComponents() {
        tituloToolbar = findViewById(R.id.text_toolbar)
        tituloToolbar.text = "Novo Anúncio"

        ibVoltar = findViewById(R.id.ib_voltar)
        etPreco = findViewById(R.id.et_preco)
        etPreco.locale = Locale("PT", "br")
    }

    private fun configClicks() {
        ibVoltar.setOnClickListener {
            finish()
        }
    }
}