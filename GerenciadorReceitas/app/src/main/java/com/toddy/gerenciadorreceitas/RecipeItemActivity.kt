package com.toddy.gerenciadorreceitas

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView

class RecipeItemActivity : AppCompatActivity() {
    private var tvReceitaNome: TextView? = null
    private var tvReceitaDescricao: TextView? = null
    private var ll_ingredientes: LinearLayout? = null
    private var receita = Receita()
    private var ingredientList: MutableList<String> = ArrayList()
    private var tvTxtToolbar:TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_item)
        tvReceitaNome = findViewById(R.id.tv_receita_nome_item)
        tvReceitaDescricao = findViewById(R.id.tv_Receita_descricao_item)
        ll_ingredientes = findViewById(R.id.ll_ingredientes)
        tvTxtToolbar = findViewById(R.id.toolbar_txt)

        tvTxtToolbar!!.text = "Minha Receita"

        carregarReceita()
        createTVIngredientes()
    }

    fun createTVIngredientes() {
        ingredientList = receita.ingredientes.split(",") as MutableList<String>

        for (txt in ingredientList) {
            var textView = TextView(this)
            textView.text = " - $txt"
            textView.setTextColor(Color.parseColor("#363537"))
            textView.textSize = 18f
            ll_ingredientes!!.addView(textView)
        }

    }

    fun carregarReceita() {
        receita.id = 1
        receita.receita = "Abóbora com Espinhafre"
        receita.descricao =
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
        receita.ingredientes = "espinhafre, abóbora, 2 colheres de açucar, 3 batatas doces"

        tvReceitaNome!!.text = receita.receita
        tvReceitaDescricao!!.text = receita.descricao

    }
}