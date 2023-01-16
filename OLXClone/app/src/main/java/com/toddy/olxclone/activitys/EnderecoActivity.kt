package com.toddy.olxclone.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.toddy.olxclone.R
import com.toddy.olxclone.model.Endereco

class EnderecoActivity : AppCompatActivity() {
    private lateinit var etCep: EditText
    private lateinit var etUf: EditText
    private lateinit var etMunicipio: EditText
    private lateinit var etBairro: EditText
    private lateinit var progressBar: ProgressBar

    private var endereco = Endereco()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_endereco)

        initComponents()
        recuperaEndereco()
    }

    private fun configEndereco(endereco: Endereco){
        etCep.setText(endereco.cep)
        etUf.setText(endereco.uf)
        etMunicipio.setText(endereco.municipio)
        etBairro.setText(endereco.bairro)
        progressBar.visibility = View.GONE
    }


    private fun recuperaEndereco() {
        progressBar.visibility = View.VISIBLE

        FirebaseDatabase.getInstance().reference
            .child("enderecos")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){

                        endereco = snapshot.getValue(Endereco::class.java)!!
                        configEndereco(endereco)
                    }else{
                        progressBar.visibility = View.GONE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
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


                endereco.cep = cep
                endereco.uf = uf
                endereco.municipio = municipio
                endereco.bairro = bairro
                endereco.salvar(this, progressBar)


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