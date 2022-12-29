package com.example.controledeprodutos.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controledeprodutos.adapter.AdapterProdutosKoltin
import com.example.controledeprodutos.ProdutoDAO
import com.example.controledeprodutos.models.ProdutoEntity
import com.example.controledeprodutos.R
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import com.tsuryo.swipeablerv.SwipeableRecyclerView


class MainActivity : AppCompatActivity(), AdapterProdutosKoltin.OnClick {

    private var produtosList = mutableListOf<ProdutoEntity>()
    private var rvProdutos: SwipeableRecyclerView? = null
    private var adapterProdutos: AdapterProdutosKoltin? = null
    private var ibAdd:ImageButton? = null
    private var ibMore:ImageButton? = null
    private var produtoDAO: ProdutoDAO? = null
    private var tvInfo:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        produtoDAO = ProdutoDAO(this)
        produtosList = produtoDAO!!.getListProdutos()
        rvProdutos = findViewById(R.id.produtos)
        ibAdd = findViewById(R.id.ib_add)
        ibMore = findViewById(R.id.ib_more)
        tvInfo = findViewById(R.id.tv_info)

//        val toolbar: Toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//        carregaLista()

        configReciclerView()

        listenerClicks()

    }

    private fun listenerClicks() {
        ibAdd!!.setOnClickListener{
            startActivity(Intent(this, FormProdutoActivity::class.java))
        }

        ibMore!!.setOnClickListener{
            val popupMenu:PopupMenu = PopupMenu(this, ibMore)
            popupMenu.menuInflater.inflate(R.menu.toolbar, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                if (it.itemId == R.id.menu_sobre){
                    Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show()
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

    }

    private fun configReciclerView() {
        produtosList.clear()
        produtosList = produtoDAO!!.getListProdutos()

        verificaQtdLista()

        rvProdutos?.layoutManager = LinearLayoutManager(this)
        rvProdutos?.setHasFixedSize(true)
        adapterProdutos = AdapterProdutosKoltin(produtosList, this)
        rvProdutos!!.adapter = adapterProdutos

        rvProdutos!!.setListener(object : SwipeLeftRightCallback.Listener {
            override fun onSwipedLeft(position: Int) {

            }

            override fun onSwipedRight(position: Int) {
                produtoDAO!!.deletaProduto(produtosList[position])
                produtosList.remove(produtosList[position])
                adapterProdutos!!.notifyItemRemoved(position)

                verificaQtdLista()
            }
        })

    }

    private fun verificaQtdLista(){
        if(produtosList.size == 0){
            tvInfo!!.visibility = View.VISIBLE
        }else{
            tvInfo!!.visibility = View.GONE
        }
    }

    override fun onStart() {
        super.onStart()
        configReciclerView()
    }

    override fun onClickListener(produto: ProdutoEntity) {
        val intent = Intent(this, FormProdutoActivity::class.java)
        intent.putExtra("produto",produto)
        startActivity(intent)
    }

//    private fun carregaLista() {
//
//        val produto1 = ProdutosKotlin("Monitor 16g", 2, 44.5)
//        val produto2 = ProdutosKotlin("Dogao", 25, 12.5)
//        val produto3 = ProdutosKotlin("Tomada 3 eixos azul", 3, 1.5)
//        val produto4 = ProdutosKotlin("Abacaxi", 5, 0.80)
//        val produto5 = ProdutosKotlin("Melancia", 8, 0.87)
//        val produto6 = ProdutosKotlin("Maça", 7, 0.48)
//        val produto7 = ProdutosKotlin("Tomate", 8, 0.72)
//        val produto8 = ProdutosKotlin("Caju", 12, 1.25)
//
//        produtosList.add(produto1)
//        produtosList.add(produto2)
//        produtosList.add(produto3)
//        produtosList.add(produto4)
//        produtosList.add(produto5)
//        produtosList.add(produto6)
//        produtosList.add(produto7)
//
//        produtosList.add(produto1)
//        produtosList.add(produto2)
//        produtosList.add(produto3)
//        produtosList.add(produto4)
//        produtosList.add(produto5)
//        produtosList.add(produto6)
//        produtosList.add(produto7)
//
//    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        MenuInflater(this).inflate(R.menu.menu_toolbar,menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val idMenu:Int = item.itemId
//
//        if (idMenu == R.id.menu_add){
//            Toast.makeText(this,"add", Toast.LENGTH_SHORT).show()
//        }else{
//            Toast.makeText(this,"sobre", Toast.LENGTH_SHORT).show()
//        }
//        return true
//    }
}