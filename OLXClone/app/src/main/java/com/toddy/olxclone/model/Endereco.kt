package com.toddy.olxclone.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Endereco {

    var cep: String? = null
    var uf: String? = null
    var municipio: String? = null
    var bairro: String? = null

    fun salvar() {
        FirebaseDatabase.getInstance().reference
            .child("enderecos")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(this)
    }
}