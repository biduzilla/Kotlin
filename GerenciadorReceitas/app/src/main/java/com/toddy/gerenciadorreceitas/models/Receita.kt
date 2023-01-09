package com.toddy.gerenciadorreceitas.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class Receita(reference: DatabaseReference = FirebaseDatabase.getInstance().reference) :
    java.io.Serializable {
    var id: String = ""
    var idUser: String = ""
    var receita: String = ""
    var descricao: String = ""
    var ingredientes: String = ""
    var urlImagem: String = ""
    var isSalgada: Boolean = false

    init {
        this.id = reference.push().key!!
    }

    fun salvarReceita() {
        FirebaseDatabase.getInstance().reference
            .child("receitas")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(this.id)
            .setValue(this)

        FirebaseDatabase.getInstance().reference
            .child("receitas_publicas")
            .child(this.id)
            .setValue(this)
    }

    fun deletarReceita() {
        FirebaseDatabase.getInstance().reference
            .child("receitas")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(this.id)
            .removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    FirebaseStorage.getInstance().reference
                        .child("imagens")
                        .child("receitas")
                        .child(this.id + "jpg")
                        .delete()
                }
            }

        FirebaseDatabase.getInstance().reference
            .child("receitas_publicas")
            .child(this.id)
            .removeValue()
    }
}