package com.example.controledeprodutos

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "produtos.db"
        private const val TB_PRODUTO: String = "TB_PRODUTO"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val sql: String =
            "CREATE TABLE IF NOT EXISTS " + TB_PRODUTO + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nome TEXT NOT NULL, " +
                    "estoque INTEGER NOT NULL, " +
                    "valor DOUBLE NOT NULL);"

        try {
            p0!!.execSQL(sql)
        }catch (e: java.lang.Exception){
            Log.i("ERROR", "onCreate" + e.message)
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}