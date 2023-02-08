package com.toddy.olxclone.activitys

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toddy.olxclone.R
import com.toddy.olxclone.adapter.AdapterCategoria
import com.toddy.olxclone.model.Categoria
import com.toddy.olxclone.utils.CategoriaList

class CategoriasActivity : AppCompatActivity(), AdapterCategoria.OnClickListener {
    private lateinit var adapterCategoria: AdapterCategoria

    private lateinit var ibVoltar: ImageButton
    private lateinit var rvCategorias: RecyclerView

    private var todas = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        initComponents()
        val bundle = intent.extras
        if (bundle != null){
            todas = bundle.getBoolean("todas")
        }

        configClicks()
        initRV()
    }

    private fun configClicks() {
        ibVoltar.setOnClickListener {
            finish()
        }
    }

    private fun initComponents() {
        val titulo: TextView = findViewById(R.id.text_toolbar)
        titulo.text = "Categorias"

        ibVoltar = findViewById(R.id.ib_voltar)
        rvCategorias = findViewById(R.id.rv_categorias)
    }

    private fun initRV() {
        rvCategorias.layoutManager = LinearLayoutManager(this)
        rvCategorias.setHasFixedSize(true)

        adapterCategoria = AdapterCategoria(CategoriaList.getList(todas), this)

        rvCategorias.adapter = adapterCategoria
    }

    override fun OnClick(categoria: Categoria) {
        val intent = Intent()
        intent.putExtra("categoriaSelecionada",categoria)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}