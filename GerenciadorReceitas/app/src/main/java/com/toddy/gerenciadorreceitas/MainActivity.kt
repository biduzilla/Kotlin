package com.toddy.gerenciadorreceitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private var etReceita:EditText? = null
    private var etDescricao:EditText? = null
    private var etIngredientes:EditText? = null
    private var ingredientesList: MutableList<String> = arrayListOf()
    private var btnSalvar:Button? = null
    private var receita = Receita()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etReceita = findViewById(R.id.et_receita)
        etDescricao = findViewById(R.id.et_descricao)
        etIngredientes = findViewById(R.id.et_ingredientes)
        btnSalvar = findViewById(R.id.btn_salvar)

    }

    fun salvarProduto(view: View){
        val nomeReceita:String = etReceita!!.text.toString()
        val descricao:String = etDescricao!!.text.toString()
        val ingredientes:String = etIngredientes!!.text.toString()
        if (nomeReceita.isNotEmpty()){
            if (descricao.isNotEmpty()){
                if (ingredientes.isNotEmpty()){
                    if (ingredientes.contains(",")){
                        ingredientesList = ingredientes.split(",") as MutableList<String>
                        receita.receita = nomeReceita
                        receita.descricao = descricao
                        receita.ingredientes = ingredientesList
                        Toast.makeText(this,receita.toString(),Toast.LENGTH_SHORT).show()
                    }else{
                        etIngredientes!!.error = "Separe os ingredientes por vírgula!"
                    }
                }else{
                    etIngredientes!!.error = "Escreva os ingredientes da receita!"
                }
            }else{
                etDescricao!!.error = "Escreva a descrição da receita!"
            }
        }else{
            etReceita!!.error = "Escreva o nome da receita!"
        }

    }



}