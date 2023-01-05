package com.toddy.casaportemporada.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import com.google.firebase.internal.InternalTokenProvider
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.model.Filtro

class FiltrarAnunciosActivity : AppCompatActivity() {

    private lateinit var tvQuarto: TextView
    private lateinit var tvBanheiro: TextView
    private lateinit var tvGaragem: TextView
    private lateinit var sbQuarto: SeekBar
    private lateinit var sbBanheiro: SeekBar
    private lateinit var sbGaragem: SeekBar
    private lateinit var ibVoltar: ImageButton


    private var qtdQuarto: Int = 0
    private var qtdBanheiro: Int = 0
    private var qtdGaragem: Int = 0

    private var filtro: Filtro? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtrar_anuncios)

        initComponets()

//        val bundle: Bundle? = intent.extras
//        if (bundle != null) {
//            filtro = bundle.getSerializable("filtro") as Filtro
//            if (filtro != null) {
//                configFiltro()
//            }
//        }

        confClicks()
        confibSb()
    }

    private fun configFiltro() {
        sbQuarto.progress = filtro!!.qtdQuarto
        sbBanheiro.progress = filtro!!.qtdBanheiro
        sbGaragem.progress = filtro!!.qtdGaragem

        tvQuarto.text = "${filtro!!.qtdQuarto} quartos ou mais"
        tvBanheiro.text = "${filtro!!.qtdBanheiro} quartos ou mais"
        tvGaragem.text = "${filtro!!.qtdGaragem} quartos ou mais"

        qtdQuarto = filtro!!.qtdQuarto
        qtdBanheiro = filtro!!.qtdBanheiro
        qtdGaragem = filtro!!.qtdGaragem
    }

    fun filtrar(view: View) {
        filtro = Filtro()
        filtro!!.qtdQuarto = qtdQuarto
        filtro!!.qtdBanheiro = qtdBanheiro
        filtro!!.qtdGaragem = qtdGaragem

        if (qtdQuarto > 0 || qtdBanheiro > 0 || qtdGaragem > 0) {
            val intent = Intent()
            intent.putExtra("filtro", filtro)
            setResult(RESULT_OK, intent)
        }
        finish()
    }

    private fun confibSb() {
        sbQuarto.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvQuarto.text = "${p1} quartos ou mais"
                qtdQuarto = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        sbBanheiro.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvBanheiro.text = "${p1} banheiros ou mais"
                qtdBanheiro = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        sbGaragem.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvGaragem.text = "${p1} garagens ou mais"
                qtdGaragem = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }

    fun limparFiltro(view: View) {
        qtdQuarto = 0
        qtdBanheiro = 0
        qtdGaragem = 0

        finish()
    }

    private fun confClicks() {
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