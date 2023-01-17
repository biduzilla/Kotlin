package com.toddy.olxclone.activitys

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.blackcat.currencyedittext.CurrencyEditText
import com.toddy.olxclone.R
import com.toddy.olxclone.model.Categoria
import java.util.*

class FormActivity : AppCompatActivity() {
    private lateinit var tituloToolbar: TextView
    private lateinit var ibVoltar: ImageButton
    private lateinit var etPreco: CurrencyEditText
    private lateinit var btnCategoria: Button

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    private var categoria = Categoria()
    private var categoriaSelecionada:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initComponents()
        configClicks()
        startResult()
    }

    private fun startResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data:Intent? = it.data
                    categoria = it.data!!.getSerializableExtra("categoriaSelecionada") as Categoria
                    categoriaSelecionada = categoria.nome
                    btnCategoria.text = categoriaSelecionada
                }else{

                }
            }
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

        etPreco = findViewById(R.id.et_preco)
        etPreco.locale = Locale("PT", "br")
    }

    private fun configClicks() {
        ibVoltar.setOnClickListener {
            finish()
        }
    }
}