package com.toddy.olxclone.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.toddy.olxclone.R
import com.toddy.olxclone.adapter.EstadoAdapter
import com.toddy.olxclone.model.Estado
import com.toddy.olxclone.utils.EstadoList

class EstadosActivity : AppCompatActivity(), EstadoAdapter.OnClickListener {

    private lateinit var rvEstados:RecyclerView
    private lateinit var adapter:EstadoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estados)

        initComponets()
        confingRv()
        configClicks()
    }

    private fun configClicks(){
        findViewById<ImageButton>(R.id.ib_voltar).setOnClickListener {
            finish()
        }
    }

    private fun confingRv(){
        rvEstados.layoutManager = LinearLayoutManager(this)
        rvEstados.setHasFixedSize(true)

        adapter = EstadoAdapter(EstadoList.getList(),this)

        rvEstados.adapter = adapter
    }

    private fun initComponets(){
        val textToolbar:TextView = findViewById(R.id.text_toolbar)
        textToolbar.text = "Estados"

        rvEstados = findViewById(R.id.rv_estados)

    }

    override fun onClickListener(estado: Estado) {
        val intent = Intent(this, RegioesActivity::class.java)
        intent.putExtra("estadoSelecionado", estado.uf)
        startActivity(intent)
    }
}