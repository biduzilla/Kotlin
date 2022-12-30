package com.example.controledeprodutos.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controledeprodutos.adapter.AdapterProdutosKoltin
import com.example.controledeprodutos.ProdutoDAO
import com.example.controledeprodutos.models.ProdutoEntity
import com.example.controledeprodutos.R
import com.example.controledeprodutos.auth.LoginActivity
import com.example.controledeprodutos.helper.FireBaseHelper
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import com.tsuryo.swipeablerv.SwipeableRecyclerView
import java.util.Collections


class MainActivity : AppCompatActivity(), AdapterProdutosKoltin.OnClick {

    private var produtosList = mutableListOf<ProdutoEntity>()
    private var rvProdutos: SwipeableRecyclerView? = null
    private var adapterProdutos: AdapterProdutosKoltin? = null
    private var ibAdd: ImageButton? = null
    private var ibMore: ImageButton? = null
    private var produtoDAO: ProdutoDAO? = null
    private var tvInfo: TextView? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var progressBar: ProgressBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadComponents()
        configReciclerView()
        listenerClicks()

    }

    override fun onStart() {
        super.onStart()
        recuperaProdutos()
    }

    private fun loadComponents() {
        database = Firebase.database.reference
        auth = FirebaseAuth.getInstance()
        produtoDAO = ProdutoDAO(this)
//        produtosList = produtoDAO!!.getListProdutos()
        rvProdutos = findViewById(R.id.produtos)
        ibAdd = findViewById(R.id.ib_add)
        ibMore = findViewById(R.id.ib_more)
        tvInfo = findViewById(R.id.tv_info)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun listenerClicks() {
        ibAdd!!.setOnClickListener {
            startActivity(Intent(this, FormProdutoActivity::class.java))
        }

        ibMore!!.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, ibMore)
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
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

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
                val produto:ProdutoEntity = produtosList[position]
                produtosList.remove(produtosList[position])
                produto.deletarProduto()
                adapterProdutos!!.notifyItemRemoved(position)

                verificaQtdLista()
            }
        })

    }

    private fun verificaQtdLista() {
        if (produtosList.size == 0) {
            tvInfo!!.text = "Nenhum Produto Cadastrado"
            tvInfo!!.visibility = View.VISIBLE
        } else {
            tvInfo!!.visibility = View.GONE
        }
        progressBar!!.visibility = View.GONE
    }

    override fun onClickListener(produto: ProdutoEntity) {
        val intent = Intent(this, FormProdutoActivity::class.java)
        intent.putExtra("produto", produto)
        startActivity(intent)
    }

    private fun recuperaProdutos() {
        val ref = FirebaseDatabase.getInstance().getReference("produtos")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                produtosList.clear()
                children.forEach{
                    it.getValue(ProdutoEntity::class.java)?.let { it1 -> produtosList.add(it1) }
                }
                verificaQtdLista()
                produtosList.reverse()
                adapterProdutos!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}