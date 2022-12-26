package com.example.controledeprodutos

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class ProdutoDAO(private val context: Context, private val dbHelper: DbHelper = DbHelper(context),private val write:SQLiteDatabase = dbHelper.writableDatabase,private val read:SQLiteDatabase = dbHelper.readableDatabase) {

    fun salvarProduto(produto:ProdutoEntity){
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

    @SuppressLint("Range")
    fun getListProdutos():MutableList<ProdutoEntity>{
        var produtoList: MutableList<ProdutoEntity> = ArrayList()
        val sql:String = "SELECT * FROM " + DbHelper.TB_PRODUTO + ";"
        val cursor:Cursor = read.rawQuery(sql,null)

        while (cursor.moveToNext()){
            val id:Int = cursor.getInt(cursor.getColumnIndex("id"))
            val nome:String = cursor.getString(cursor.getColumnIndex("nome"))
            val estoque:Int = cursor.getInt(cursor.getColumnIndex("estoque"))
            val valor:Double = cursor.getDouble(cursor.getColumnIndex("valor"))

            val produto = ProdutoEntity(nome,estoque,valor)
            produto.id = id

            produtoList.add(produto)
        }
        return produtoList
    }


}