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
import com.example.controledeprodutos.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadComponents()
        configReciclerView()
        listenerClicks()

    }

    private fun loadComponents(){
        auth = FirebaseAuth.getInstance()
        produtoDAO = ProdutoDAO(this)
        produtosList = produtoDAO!!.getListProdutos()
        rvProdutos = findViewById(R.id.produtos)
        ibAdd = findViewById(R.id.ib_add)
        ibMore = findViewById(R.id.ib_more)
        tvInfo = findViewById(R.id.tv_info)
    }

    private fun listenerClicks() {
        ibAdd!!.setOnClickListener{
            startActivity(Intent(this, FormProdutoActivity::class.java))
        }

        ibMore!!.setOnClickListener{
            val popupMenu:PopupMenu = PopupMenu(this, ibMore)
            popupMenu.menuInflater.inflate(R.menu.toolbar, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_sobre -> {
                        Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show()
                    }
                    R.id.menu_sair -> {
                        auth.signOut()
                        startActivity(Intent(this, SplashActivity::class.java))
                    }
                }

//                if (it.itemId == R.id.menu_sobre){
//                    Toast.makeText(this, "Sobre", Toast.LENGTH_SHORT).show()
//                }
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

}