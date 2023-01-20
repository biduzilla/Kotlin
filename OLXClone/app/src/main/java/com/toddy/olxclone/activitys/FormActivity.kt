package com.toddy.olxclone.activitys

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.blackcat.currencyedittext.CurrencyEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.santalu.maskara.widget.MaskEditText
import com.toddy.olxclone.R
import com.toddy.olxclone.model.Categoria
import com.toddy.olxclone.model.Endereco
import org.w3c.dom.Text
import java.util.*

class FormActivity : AppCompatActivity() {
    private lateinit var tituloToolbar: TextView
    private lateinit var ibVoltar: ImageButton
    private lateinit var etPreco: CurrencyEditText
    private lateinit var btnCategoria: Button
    private lateinit var etCep: MaskEditText
    private lateinit var progressBar: ProgressBar

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var categoria = Categoria()
    private var endereco = Endereco()
    private var categoriaSelecionada: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initComponents()
        configClicks()
        startResult()
        recuperaEndereco()
    }

    private fun startResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = it.data
                    categoria = it.data!!.getSerializableExtra("categoriaSelecionada") as Categoria
                    categoriaSelecionada = categoria.nome
                    btnCategoria.text = categoriaSelecionada
                } else {

                }
            }
    }

    private fun recuperaEndereco() {
        configCep()
        FirebaseDatabase.getInstance().reference
            .child("enderecos")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    endereco = snapshot.getValue(Endereco::class.java)!!
                    etCep.setText(endereco.cep)
                    progressBar.visibility = View.GONE

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun configCep() {
        etCep.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val cep: String = p0.toString().replace("_","").replace("-","")

                if (cep.length == 8){
                    buscarEndereco(cep)
                }else{
                    progressBar.visibility = View.GONE
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun buscarEndereco(cep:String){
        progressBar.visibility = View.VISIBLE

    }

    fun selecionarCategoria(view: View) {
        val intent = Intent(this, CategoriasActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun initComponents() {
        tituloToolbar = findViewById(R.id.text_toolbar)
        tituloToolbar.text = "Novo Anúncio"

        btnCategoria = findViewById(R.id.btn_categorias)
        ibVoltar = findViewById(R.id.ib_voltar)
        etCep = findViewById(R.id.et_cep)
        progressBar = findViewById(R.id.progress_bar)

        etPreco = findViewById(R.id.et_preco)
        etPreco.locale = Locale("PT", "br")
    }

    private fun configClicks() {
        ibVoltar.setOnClickListener {
            finish()
        }
    }
}