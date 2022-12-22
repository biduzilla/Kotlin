package com.example.controledeprodutos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private var produtosList = mutableListOf<ProdutosKotlin>()
    private var rvProdutos: RecyclerView? = null
    private var adapterProdutos: AdapterProdutosKoltin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvProdutos = findViewById(R.id.produtos)

        carregaLista()

        configReciclerView()

    }

    private fun configReciclerView() {
        rvProdutos?.layoutManager = LinearLayoutManager(this)
        rvProdutos?.setHasFixedSize(true)
        adapterProdutos = AdapterProdutosKoltin(produtosList)
        rvProdutos!!.adapter = adapterProdutos

    }

    private fun carregaLista() {

        val produto1 = ProdutosKotlin("Monitor 16g", 2, 44.5)
        val produto2 = ProdutosKotlin("Dogao", 25, 12.5)
        val produto3 = ProdutosKotlin("Tomada 3 eixos azul", 3, 1.5)
        val produto4 = ProdutosKotlin("Abacaxi", 5, 0.80)

        produtosList.add(produto1)
        produtosList.add(produto2)
        produtosList.add(produto3)
        produtosList.add(produto4)
    }
}