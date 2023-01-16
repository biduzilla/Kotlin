package com.toddy.olxclone.model

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Endereco {

    var cep: String? = null
    var uf: String? = null
    var municipio: String? = null
    var bairro: String? = null

    fun salvar(context: Context, progressBar: ProgressBar) {
        FirebaseDatabase.getInstance().reference
            .child("enderecos")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(this).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Endereço salvo com sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Problema ao salvar endereço", Toast.LENGTH_SHORT)
                        .show()
                }
                progressBar.visibility = View.GONE
            }
    }
}