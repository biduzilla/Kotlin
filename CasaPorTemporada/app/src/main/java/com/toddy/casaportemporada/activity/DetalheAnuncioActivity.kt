package com.toddy.casaportemporada.activity

import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.model.Anuncio
import com.toddy.casaportemporada.model.Usuario

class DetalheAnuncioActivity : AppCompatActivity() {
    private lateinit var imagemAnuncio: ImageView
    private lateinit var ibVoltar: ImageButton
    private lateinit var titulo: TextView
    private lateinit var descricao: TextView
    private lateinit var tvQuartos: TextView
    private lateinit var tvBanheiro: TextView
    private lateinit var tvGaragem: TextView

    private var anuncio: Anuncio? = null
    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_anuncio)

        initComponets()

        anuncio = intent.getSerializableExtra("anuncio") as Anuncio
        recuperaAnuciante()
        configClicks()
        configDados()
    }

    private fun configDados() {
        if (anuncio != null) {
            Picasso.get().load(anuncio!!.urlImage).into(imagemAnuncio)
            titulo.text = anuncio!!.titulo
            descricao.text = anuncio!!.descricao
            tvQuartos.text = anuncio!!.quarto
            tvBanheiro.text = anuncio!!.banheiro
            tvGaragem.text = anuncio!!.garagem
        } else {
            Toast.makeText(this, "Não foi possível carregar o anúncio", Toast.LENGTH_SHORT).show()
        }
    }

    private fun configClicks() {
        ibVoltar.setOnClickListener {
            finish()
        }
    }

    private fun initComponets() {
        val tvTituloToolbar: TextView = findViewById(R.id.tv_titulo_toolbar_voltar)
        tvTituloToolbar.text = "Mais Detalhes"

        ibVoltar = findViewById(R.id.ib_toolbar_voltar)
        imagemAnuncio = findViewById(R.id.iv_anuncio)
        titulo = findViewById(R.id.tv_titulo_anuncio)
        descricao = findViewById(R.id.tv_descricao_anuncio)
        tvQuartos = findViewById(R.id.tv_quarto)
        tvGaragem = findViewById(R.id.tv_garagem)
        tvBanheiro = findViewById(R.id.tv_banheiro)
    }

    private fun recuperaAnuciante() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("usuario").child(anuncio!!.idUser)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               usuario = snapshot.getValue(Usuario::class.java)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun ligar(view: View) {
        if (usuario != null) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${usuario!!.telefone}")
            startActivity(intent)
        } else {
            Toast.makeText(this, "Carregando Informações, aguarde", Toast.LENGTH_SHORT).show()
        }
    }
}