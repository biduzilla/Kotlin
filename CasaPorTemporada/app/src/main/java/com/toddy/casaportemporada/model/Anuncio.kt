package com.toddy.casaportemporada.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Anuncio(reference: DatabaseReference = FirebaseDatabase.getInstance().reference) {
    var id: String = ""
    var titulo: String = ""
    var descricao: String = ""
    var quarto: String = ""
    var banheiro: String = ""
    var garagem: String = ""
    var urlImage: String = ""
    var status: Boolean = false

    init {
        this.id = reference.push().key!!
    }

    fun salvarAnuncio() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("anuncios")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(this.id)

        reference.setValue(this)
    }
}




