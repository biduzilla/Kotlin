package com.example.controledeprodutos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FormProdutoActivity : AppCompatActivity() {
    private var editProduto: EditText? = null
    private var editQuantidade: EditText? = null
    private var editValor: EditText? = null
    private var produtoDAO: ProdutoDAO? = null
    private var produto = ProdutoEntity()
    private var btnForm:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_produto)

        produtoDAO = ProdutoDAO(this)
        editProduto = findViewById(R.id.edit_produto)
        editQuantidade = findViewById(R.id.edit_quantidade)
        editValor = findViewById(R.id.edit_valor)
        btnForm =findViewById(R.id.btn_form)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            produto = bundle.getSerializable("produto") as ProdutoEntity
            btnForm!!.text = "atualizar"
            editProduto()
        }
    }

    fun voltarHome(view:View){
        startActivity(Intent(this,MainActivity::class.java))

    }

    private fun editProduto() {
        editProduto!!.setText(produto.nome)
        editQuantidade!!.setText(produto.estoque.toString())
        editValor!!.setText(produto.valor.toString())
    }

    fun salvarProduto(view: View) {
        val nome: String = editProduto!!.text.toString()
        val quantidade: String = editQuantidade!!.text.toString()
        val valor: String = editValor!!.text.toString()

        if (nome.isNotEmpty()) {
            if (quantidade.isNotEmpty()) {
                val qtd: Int = quantidade.toInt()

                if (qtd >= 1) {
                    if (valor.isNotEmpty()) {
                        val valorProduto: Double = valor.toDouble()
                        if (valorProduto > 0) {

                            produto.nome = nome
                            produto.estoque = qtd
                            produto.valor = valorProduto
                            if (produto.id != 0) {
                                produtoDAO!!.atualizaProduto(produto)
                            } else {
                                produtoDAO!!.salvarProduto(produto)
                            }
                            finish()

                        } else {
                            editValor!!.requestFocus()
                            editValor!!.error = "Informe um valor maior que 0"
                        }
                    } else {
                        editValor!!.requestFocus()
                        editValor!!.error = "Informe o Valor do Produto"
                    }
                } else {
                    editQuantidade!!.requestFocus()
                    editQuantidade!!.error = "Informe um valor maior que 0"
                }
            } else {
                editQuantidade!!.requestFocus()
                editQuantidade!!.error = "Informe a quantidade do produto"
            }
        } else {
            editProduto!!.requestFocus()
            editProduto!!.error = "Informe o nome do produto"
        }
    }

}