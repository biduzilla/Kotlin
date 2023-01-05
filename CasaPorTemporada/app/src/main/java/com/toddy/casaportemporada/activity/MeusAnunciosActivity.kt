package com.toddy.casaportemporada.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.adapter.AdapterAnuncios
import com.toddy.casaportemporada.model.Anuncio
import org.w3c.dom.Text

class MeusAnunciosActivity : AppCompatActivity(), AdapterAnuncios.OnClick {

    private var anunciosList = mutableListOf<Anuncio>()

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvInfo: TextView
    private lateinit var adapter: AdapterAnuncios
    private lateinit var ibAdd: ImageButton
    private lateinit var ibVoltar: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_anuncios)

        initComponents()
        configRv()
        configClicks()
    }

    override fun onStart() {
        super.onStart()
        recuperaAnuncios()
    }

    private fun configClicks(){
        ibVoltar.setOnClickListener {
            finish()
        }
        ibAdd.setOnClickListener {
            startActivity(Intent(this, FormAnuncioActivity::class.java))
        }
    }



    private fun initComponents() {
        val tvTitulo: TextView = findViewById(R.id.tv_titulo_toolbar_voltar)
        tvTitulo.text = "Meus Anúncios"

        ibAdd = findViewById(R.id.ib_toolbar_add)
        ibVoltar = findViewById(R.id.ib_toolbar_voltar)
        progressBar = findViewById(R.id.progress_bar)
        recyclerView = findViewById(R.id.rv_anuncios)
        tvInfo = findViewById(R.id.tv_info)
    }

    private fun recuperaAnuncios() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("anuncios")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    anunciosList.clear()
                    children.forEach {
                        it.getValue(Anuncio::class.java).let { anuncio ->
                            anunciosList.add(anuncio!!)
                        }
                    }
                    tvInfo.visibility = View.GONE
                } else {
                    tvInfo.text = "Nenhum Anúncio Cadastrado"
                }

                progressBar.visibility = View.GONE
                anunciosList.reverse()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun configRv() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        adapter = AdapterAnuncios(anunciosList,this)

        recyclerView.adapter = adapter
    }

    override fun onClickListener(anuncio: Anuncio) {
        val intent = Intent(this,FormAnuncioActivity::class.java)
        intent.putExtra("anuncio",anuncio)
        startActivity(intent)
    }
}