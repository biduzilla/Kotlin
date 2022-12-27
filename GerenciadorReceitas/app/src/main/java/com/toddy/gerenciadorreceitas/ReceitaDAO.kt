package com.toddy.gerenciadorreceitas

import android.content.ContentValues
import android.content.Context
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

}