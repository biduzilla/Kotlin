package com.toddy.casaportemporada.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.adapter.AdapterAnuncios
import com.toddy.casaportemporada.model.Anuncio
import org.w3c.dom.Text

class MeusAnunciosActivity : AppCompatActivity() {

    private var anunciosList = mutableListOf<Anuncio>()

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvInfo: TextView
    private lateinit var adapter: AdapterAnuncios
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_anuncios)

        initComponents()
        recuperaAnuncios()
        configRv()

    }

    fun carregaAnuncios() {
        while (anunciosList.size < 4) {
            val anuncio = Anuncio()
            anuncio.id = "1"
            anuncio.descricao = "descrição"
            anuncio.urlImage =
                "https://firebasestorage.googleapis.com/v0/b/casa-por-temporada-d76d1.appspot.com/o/imagens%2Fanuncios%2F-NKt4XrXFPbFbsILjWldjpg?alt=media&token=995b5e12-a2f3-49ad-97de-04de8e018f90"
            anuncio.titulo = "titulo"
            anuncio.quarto = "1"
            anuncio.garagem = "1"
            anuncio.banheiro = "1"
            anuncio.status = false

            anunciosList.add(anuncio)
        }

    }


    private fun initComponents() {
        val tvTitulo: TextView = findViewById(R.id.tv_titulo_toolbar_voltar)
        tvTitulo.text = "Meus Anúncios"

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
//                    anunciosList.clear()
                    children.forEach {
                        it.getValue(Anuncio::class.java).let { anuncio ->
                            anunciosList.add(anuncio!!)
                        }
                    }
                    tvInfo.text = anunciosList.size.toString()
//                    tvInfo.visibility = View.GONE
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

        adapter = AdapterAnuncios(anunciosList)

        recyclerView.adapter = adapter
    }
}