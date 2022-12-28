package com.toddy.gerenciadorreceitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RecipeItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_item)
    }

    fun carregarReceita(){
        var receita = Receita()
        receita.id = 1
        receita.receita = "Abóbora com Espinhafre"

    }
}