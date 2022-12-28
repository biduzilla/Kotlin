package com.toddy.gerenciadorreceitas

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class ReceitaDAO(
    private val context: Context,
    private val dbHelper: DbHelper = DbHelper(context),
    private val write: SQLiteDatabase = dbHelper.writableDatabase,
    private val read: SQLiteDatabase = dbHelper.readableDatabase

) {
    fun salvarReceita(receita: Receita) {
        val contentValues = ContentValues()
        contentValues.put("receita", receita.receita)
        contentValues.put("descricao", receita.descricao)
        contentValues.put("ingredientes", receita.ingredientes)

        try {
            write.insert(DbHelper.TB_RECEITAS, null, contentValues)
        } catch (e: java.lang.Exception) {
            Log.i("ERROR", "Error ao salvar receita")
        }
    }

    @SuppressLint("Range")
    fun getListRecipes(): MutableList<Receita> {
        val recipeList: MutableList<Receita> = ArrayList()
        val sql: String = "SELECT * FROM " + DbHelper.TB_RECEITAS + ";"
        val cursor: Cursor = read.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val id: Int = cursor.getInt(cursor.getColumnIndex("id"))
            val receita: String = cursor.getString(cursor.getColumnIndex("receita"))
            val descricao: String = cursor.getString(cursor.getColumnIndex("descricao"))
            val ingredientes: String = cursor.getString(cursor.getColumnIndex("ingredientes"))

            val receitaTmp = Receita()
            receitaTmp.receita = receita
            receitaTmp.id = id
            receitaTmp.descricao = descricao
            receitaTmp.ingredientes = ingredientes

            recipeList.add(receitaTmp)
        }
        return recipeList
    }

    fun deletarReceita(receita: Receita){
        write.delete(DbHelper.TB_RECEITAS,"id="+receita.id,null)
    }

    fun atualizarReceita(receita: Receita){
        val contentValues = ContentValues()
        contentValues.put("id", receita.id)
        contentValues.put("receita", receita.receita)
        contentValues.put("ingredientes", receita.ingredientes)
        contentValues.put("descricao", receita.descricao)

        write.update(DbHelper.TB_RECEITAS,contentValues,"id=" + receita.id,null)
    }

}