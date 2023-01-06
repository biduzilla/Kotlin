package com.toddy.gerenciadorreceitas.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.toddy.gerenciadorreceitas.R
import com.toddy.gerenciadorreceitas.models.Receita
import com.toddy.gerenciadorreceitas.ReceitaDAO

class FormActivity : AppCompatActivity() {
    private var etReceita: EditText? = null
    private var etDescricao: EditText? = null
    private var etIngredientes: EditText? = null
    private var receitaDAO: ReceitaDAO? = null
    private var btnSalvar: Button? = null
    private var receita = Receita()
    private var ivVoltar: ImageView? = null
    private var toolbarText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        receitaDAO = ReceitaDAO(this)
        etReceita = findViewById(R.id.et_receita)
        etDescricao = findViewById(R.id.et_descricao)
        etIngredientes = findViewById(R.id.et_ingredientes)
        btnSalvar = findViewById(R.id.btn_salvar)
        ivVoltar = findViewById(R.id.iv_back)
        toolbarText = findViewById(R.id.toolbar_txt)
        isUpdate()
        editReceita()
        clickListener()
    }

    private fun editReceita() {
        etReceita?.setText(receita.receita)
        etDescricao?.setText(receita.descricao)
        etIngredientes?.setText(receita.ingredientes)
    }

    private fun isUpdate() {
        val bundle: Bundle? = intent.extras

        if (bundle != null) {
            receita = bundle.getSerializable("receita") as Receita
            btnSalvar?.text = "Atualizar"
            toolbarText?.text = "Atualizar Receita"

        }
    }

    private fun clickListener() {
        ivVoltar!!.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    fun salvarProduto(view: View) {
        val nomeReceita: String = etReceita!!.text.toString()
        val descricao: String = etDescricao!!.text.toString()
        val ingredientes: String = etIngredientes!!.text.toString()
    }
}