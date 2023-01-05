package com.toddy.casaportemporada.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.model.Anuncio

class DetalheAnuncioActivity : AppCompatActivity() {
    private lateinit var imagemAnuncio: ImageView
    private lateinit var titulo: TextView
    private lateinit var descricao: TextView
    private lateinit var tvQuartos: TextView
    private lateinit var tvBanheiro: TextView
    private lateinit var tvGaragem: TextView

    private var anuncio:Anuncio? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhe_anuncio)

        initComponets()

        anuncio = intent.getSerializableExtra("anuncio") as Anuncio

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

    private fun initComponets() {
        imagemAnuncio = findViewById(R.id.iv_anuncio)
        titulo = findViewById(R.id.tv_titulo_anuncio)
        descricao = findViewById(R.id.tv_descricao_anuncio)
        tvQuartos = findViewById(R.id.tv_quarto)
        tvGaragem = findViewById(R.id.tv_garagem)
        tvBanheiro = findViewById(R.id.tv_banheiro)
    }
}