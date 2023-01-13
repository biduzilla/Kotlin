package com.toddy.olxclone.model

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

    fun salvar() {
        FirebaseDatabase.getInstance().reference
            .child("usuarios")
            .child(this.id)
            .setValue(this)
    }
}