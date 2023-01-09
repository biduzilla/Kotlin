package com.toddy.gerenciadorreceitas.models

import android.annotation.SuppressLint
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase

class Usuario : java.io.Serializable {
    var id: String = ""
    var nome: String = ""
    var email: String = ""
    var senha: String = ""
        @Exclude
        get

    fun salvar() {
        val reference: DatabaseReference =
            FirebaseDatabase.getInstance().reference.child("usuarios").child(this.id)

        reference.setValue(this)
    }
}