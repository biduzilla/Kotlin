package com.toddy.olxclone.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.toddy.olxclone.R
import com.toddy.olxclone.model.Endereco

class EnderecoActivity : AppCompatActivity() {
    private lateinit var etCep: EditText
    private lateinit var etUf: EditText
    private lateinit var etMunicipio: EditText
    private lateinit var etBairro: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_endereco)

        initComponents()
    }

    fun validaDados(view: View) {
        val cep: String = etCep.text.toString()
        val uf: String = etUf.text.toString()
        val municipio: String = etMunicipio.text.toString()
        val bairro: String = etBairro.text.toString()

        when {
            cep.isEmpty() -> {
                etCep.requestFocus()
                etCep.error = "Campo Obrigatório"
            }
            uf.isEmpty() -> {
                etUf.requestFocus()
                etUf.error = "Campo Obrigatório"
            }
            municipio.isEmpty() -> {
                etMunicipio.requestFocus()
                etMunicipio.error = "Campo Obrigatório"
            }
            bairro.isEmpty() -> {
                etBairro.requestFocus()
                etBairro.error = "Campo Obrigatório"
            }
            else -> {
                progressBar.visibility = View.VISIBLE

                val endereco = Endereco()
                endereco.cep = cep
                endereco.uf = uf
                endereco.municipio = municipio
                endereco.bairro = bairro
                endereco.salvar()
                progressBar.visibility = View.GONE

                Toast.makeText(this, "Endereço salvo com sucesso",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun initComponents() {
        val titulo: TextView = findViewById(R.id.text_toolbar)
        titulo.text = "Endereço"

        etCep = findViewById(R.id.et_cep)
        etUf = findViewById(R.id.et_uf)
        etMunicipio = findViewById(R.id.et_cidade)
        etBairro = findViewById(R.id.et_bairro)
        progressBar = findViewById(R.id.progress_bar)
    }
}