package com.toddy.gerenciadorreceitas.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.squareup.picasso.Picasso
import com.toddy.gerenciadorreceitas.R
import com.toddy.gerenciadorreceitas.models.Receita

class RecipeItemActivity : AppCompatActivity() {
    private lateinit var fotoReceita:ImageView
    private var tvReceitaNome: TextView? = null
    private var tvReceitaDescricao: TextView? = null
    private var ll_ingredientes: LinearLayout? = null
    private var receita: Receita? = null
    private var ingredientList: MutableList<String> = ArrayList()
    private var tvTxtToolbar:TextView? = null
    private var ibVoltar:ImageButton? = null
    private var btnUpdate:Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_item)

        initComponets()
        carregarReceita()
        createTVIngredientes()
        onClickListener()
    }

    private fun initComponets(){
            tvReceitaNome = findViewById(R.id.tv_receita_nome_item)
            tvReceitaDescricao = findViewById(R.id.tv_Receita_descricao_item)
            ll_ingredientes = findViewById(R.id.ll_ingredientes)
            tvTxtToolbar = findViewById(R.id.toolbar_txt)
            ibVoltar = findViewById(R.id.iv_back)
            fotoReceita = findViewById(R.id.foto_receita)
            btnUpdate = findViewById(R.id.btn_update)
    }

    fun createTVIngredientes() {
        ingredientList = receita!!.ingredientes.split(",") as MutableList<String>

        for (txt in ingredientList) {
            var textView = TextView(this)
            textView.text = " - ${txt.trim()}"
            textView.setTextColor(Color.parseColor("#363537"))
            textView.textSize = 18f
            ll_ingredientes!!.addView(textView)
        }

    }

    fun onClickListener(){
        ibVoltar?.setOnClickListener{
            finish()
        }

        btnUpdate?.setOnClickListener{
            val intent = Intent(this, FormActivity::class.java)
            intent.putExtra("receita",receita)
            startActivity(intent)
        }
    }

    fun carregarReceita() {

        val bundle = intent.extras
        receita = Receita()
        receita = bundle?.getSerializable("receita") as Receita
        tvReceitaNome!!.text = receita!!.receita
        tvReceitaDescricao!!.text = receita!!.descricao
        Picasso.get().load(receita!!.urlImagem).into(fotoReceita)
        tvTxtToolbar!!.text = receita!!.receita
    }
}