package com.toddy.olxclone.activitys

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        initComponents()
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

        adapterCategoria = AdapterCategoria(CategoriaList.getList(false), this)

        rvCategorias.adapter = adapterCategoria
    }

    override fun OnClick(categoria: Categoria) {
        Toast.makeText(this, categoria.nome, Toast.LENGTH_SHORT).show()
    }
}