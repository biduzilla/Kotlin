package com.toddy.olxclone.helper

import android.app.Activity
import android.content.SharedPreferences
import com.toddy.olxclone.model.Estado
import com.toddy.olxclone.model.Filtro

class SPFiltro {
    companion object {
        private val ARQUIVO_REFERENCIA: String = "ArquivoPreferencia"

        fun setFiltro(activity: Activity, chave: String, valor: String) {
            val preferences: SharedPreferences =
                activity.getSharedPreferences(ARQUIVO_REFERENCIA, 0)
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString(chave, valor)
            editor.apply()
        }

        fun getFiltro(activity: Activity): Filtro {
            val preferences: SharedPreferences =
                activity.getSharedPreferences(ARQUIVO_REFERENCIA, 0)

            val pesquisa: String? = preferences.getString("pesquisa", "")
            val ufEstado: String? = preferences.getString("ufEstado", "")
            val categoria: String? = preferences.getString("categoria", "")
            val nomeEstado: String? = preferences.getString("nomeEstado", "")
            val regiao: String? = preferences.getString("regiao", "")
            val ddd: String? = preferences.getString("ddd", "")

            val valorMin: String? = preferences.getString("valorMin", "")
            val valorMax: String? = preferences.getString("valorMax", "")

            val estado = Estado(nomeEstado!!, ufEstado!!, regiao!!, ddd!!)

            val filtro = Filtro(estado, categoria!!, pesquisa!!)

             if (valorMin != ""){
                 filtro.valorMin = valorMin!!.toInt()
             }

            if (valorMax != ""){
                filtro.valorMax = valorMin!!.toInt()
            }

            return filtro
        }

        fun limparFiltros(activity: Activity) {
            setFiltro(activity, "pesquisa", "")
            setFiltro(activity, "ufEstado", "")
            setFiltro(activity, "categoria", "")
            setFiltro(activity, "nomeEstado", "")
            setFiltro(activity, "regiao", "")
            setFiltro(activity, "ddd", "")
            setFiltro(activity, "valorMin", "")
            setFiltro(activity, "valorMax", "")
        }
    }
}