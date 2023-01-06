package com.toddy.gerenciadorreceitas.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.toddy.gerenciadorreceitas.R
import com.toddy.gerenciadorreceitas.models.Receita
import com.toddy.gerenciadorreceitas.ReceitaDAO

class FormActivity : AppCompatActivity() {
    private lateinit var ivAddFoto: ImageView
    private lateinit var checkBox: CheckBox
    private lateinit var progressBar: ProgressBar
    private lateinit var etReceita: EditText
    private lateinit var etDescricao: EditText
    private lateinit var etIngredientes: EditText
    private lateinit var btnSalvar: Button
    private var receita = Receita()
    private lateinit var ivVoltar: ImageView
    private lateinit var toolbarText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        initComponets()
        isUpdate()
        editReceita()
        clickListener()
    }

    private fun initComponets() {
        ivAddFoto = findViewById(R.id.iv_add_foto)
        progressBar = findViewById(R.id.progress_bar)
        checkBox = findViewById(R.id.checkbox)
        etReceita = findViewById(R.id.et_receita)
        etDescricao = findViewById(R.id.et_descricao)
        etIngredientes = findViewById(R.id.et_ingredientes)
        btnSalvar = findViewById(R.id.btn_salvar)
        ivVoltar = findViewById(R.id.iv_back)
        toolbarText = findViewById(R.id.toolbar_txt)
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
        ivVoltar.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    fun salvarProduto(view: View) {
        val nomeReceita: String = etReceita!!.text.toString()
        val descricao: String = etDescricao!!.text.toString()
        val ingredientes: String = etIngredientes!!.text.toString()
        val checked: Boolean = checkBox.isChecked

        when {
            nomeReceita.isEmpty() -> {
                etReceita.requestFocus()
                etReceita.error = "Campo Obrigatório"
            }
            descricao.isEmpty() -> {
                etDescricao.requestFocus()
                etDescricao.error = "Campo Obrigatório"
            }
            ingredientes.isEmpty() -> {
                etIngredientes.requestFocus()
                etIngredientes.error = "Campo Obrigatório"
            }
            !ingredientes.contains(",") ->{
                etIngredientes.requestFocus()
                etIngredientes.error = "Separe os ingredientes por vírgula"
            }
            else -> {

            }
        }
    }
}