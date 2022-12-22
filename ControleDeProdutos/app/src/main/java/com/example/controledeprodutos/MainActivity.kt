package com.example.controledeprodutos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import com.tsuryo.swipeablerv.SwipeableRecyclerView


class MainActivity : AppCompatActivity(), AdapterProdutosKoltin.OnClick {

    private var produtosList = mutableListOf<ProdutosKotlin>()
    private var rvProdutos: SwipeableRecyclerView? = null
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
        adapterProdutos = AdapterProdutosKoltin(produtosList, this)
        rvProdutos!!.adapter = adapterProdutos

        rvProdutos!!.setListener(object : SwipeLeftRightCallback.Listener {
            override fun onSwipedLeft(position: Int) {

            }

            override fun onSwipedRight(position: Int) {

            }
        })

    }

    private fun carregaLista() {

        val produto1 = ProdutosKotlin("Monitor 16g", 2, 44.5)
        val produto2 = ProdutosKotlin("Dogao", 25, 12.5)
        val produto3 = ProdutosKotlin("Tomada 3 eixos azul", 3, 1.5)
        val produto4 = ProdutosKotlin("Abacaxi", 5, 0.80)
        val produto5 = ProdutosKotlin("Melancia", 8, 0.87)
        val produto6 = ProdutosKotlin("Maça", 7, 0.48)
        val produto7 = ProdutosKotlin("Tomate", 8, 0.72)
        val produto8 = ProdutosKotlin("Caju", 12, 1.25)

        produtosList.add(produto1)
        produtosList.add(produto2)
        produtosList.add(produto3)
        produtosList.add(produto4)
        produtosList.add(produto5)
        produtosList.add(produto6)
        produtosList.add(produto7)

        produtosList.add(produto1)
        produtosList.add(produto2)
        produtosList.add(produto3)
        produtosList.add(produto4)
        produtosList.add(produto5)
        produtosList.add(produto6)
        produtosList.add(produto7)

    }

    override fun onClickListener(produto: ProdutosKotlin) {
        Toast.makeText(this, produto.nome, Toast.LENGTH_SHORT).show()
    }
}