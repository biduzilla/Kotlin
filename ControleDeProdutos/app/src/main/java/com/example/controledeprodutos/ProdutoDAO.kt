package com.example.controledeprodutos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class ProdutoDAO(private val context: Context, private val dbHelper: DbHelper = DbHelper(context),private val write:SQLiteDatabase = dbHelper.writableDatabase,private val read:SQLiteDatabase = dbHelper.readableDatabase) {

    fun salvarProduto(produto:ProdutosKotlin){
        val contentValues:ContentValues = ContentValues()
        contentValues.put("nome",produto.nome)
        contentValues.put("estoque",produto.estoque)
        contentValues.put("valor",produto.valor)

        try {
            write.insert(DbHelper.TB_PRODUTO,null, contentValues)
//            write.close()
        }catch (e:java.lang.Exception){
            println("Error salvar produto - " + e.message)
        }


    }


}