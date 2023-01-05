package com.toddy.casaportemporada.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import com.toddy.casaportemporada.R

class FiltrarAnunciosActivity : AppCompatActivity() {

    private lateinit var tvQuarto: TextView
    private lateinit var tvBanheiro: TextView
    private lateinit var tvGaragem: TextView
    private lateinit var sbQuarto: SeekBar
    private lateinit var sbBanheiro: SeekBar
    private lateinit var sbGaragem: SeekBar
    private lateinit var ibVoltar:ImageButton


    private var qtdQuarto: Int = 0
    private var qtdBanheiro: Int = 0
    private var qtdGaragem: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtrar_anuncios)

        initComponets()
        confClicks()
    }

    fun limparFiltro(view: View){
        qtdQuarto = 0
        qtdBanheiro = 0
        qtdGaragem = 0

        finish()
    }

    private fun confClicks(){
        ibVoltar.setOnClickListener {
            finish()
        }
    }

    private fun initComponets() {
        val tituloToolbar: TextView = findViewById(R.id.tv_titulo_toolbar_voltar)
        tituloToolbar.text = "Filtrar Pesquisa"

        ibVoltar = findViewById(R.id.ib_toolbar_voltar)
        tvQuarto = findViewById(R.id.tv_quarto)
        tvBanheiro = findViewById(R.id.tv_banheiro)
        tvGaragem = findViewById(R.id.tv_garagem)
        sbQuarto = findViewById(R.id.sb_quarto)
        sbBanheiro = findViewById(R.id.sb_banheiro)
        sbGaragem = findViewById(R.id.sb_garagem)
    }
}