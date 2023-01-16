package com.toddy.olxclone.model

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase

class User {
    var id: String = ""
    var nome: String = ""
    var email: String = ""
    var telefone: String = ""
    var senha: String = ""
        @Exclude
        get
    var imagemPerfil: String? = null

    fun salvar(progressBar: ProgressBar, context: Context) {
        FirebaseDatabase.getInstance().reference
            .child("usuarios")
            .child(this.id)
            .setValue(this)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Imagem salva com sucesso", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Erro no upload, tente mais tarde", Toast.LENGTH_SHORT)
                        .show()
                }
                progressBar.visibility = View.GONE
            }
    }
}