package com.toddy.olxclone.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.toddy.olxclone.R
import com.toddy.olxclone.adapter.EstadoAdapter
import com.toddy.olxclone.adapter.RegiaoAdapter
import com.toddy.olxclone.helper.SPFiltro
import com.toddy.olxclone.utils.RegioesList

class RegioesActivity : AppCompatActivity(), RegiaoAdapter.OnClickListener {

    private lateinit var rvRegioes: RecyclerView
    private lateinit var adapter: RegiaoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regioes)

        initComponents()


        configRv()
        configClicks()
    }

    private fun configRv() {
        rvRegioes.layoutManager = LinearLayoutManager(this)
        rvRegioes.setHasFixedSize(true)
        adapter = RegiaoAdapter(RegioesList.getList(SPFiltro.getFiltro(this).estado!!.uf), this)
        rvRegioes.adapter = adapter
    }

    private fun configClicks() {
        findViewById<ImageButton>(R.id.ib_voltar).setOnClickListener {
            finish()
        }
    }

    private fun initComponents() {
        findViewById<TextView>(R.id.text_toolbar).text = "Selecione a região"
        rvRegioes = findViewById(R.id.rv_regioes)
    }

    override fun onClickListener(regiao: String) {
        Toast.makeText(this, regiao, Toast.LENGTH_SHORT).show()
    }
}