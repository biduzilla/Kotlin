package com.toddy.casaportemporada.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase

class Usuario {
    var id: String = ""
    var nome: String = ""
    var telefone: String = ""
    var email: String = ""

    var senha: String = ""
        @Exclude
        get


    fun salvar() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("usuario")
            .child(this.id)

        reference.setValue(this)
    }

}