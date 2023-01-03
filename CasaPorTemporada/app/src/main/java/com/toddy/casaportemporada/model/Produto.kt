package com.toddy.casaportemporada.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Produto(reference: DatabaseReference = FirebaseDatabase.getInstance().reference) {
    var id: String = ""
    var titulo: String = ""
    var descricao: String = ""
    var quarto: String = ""
    var banheiro: String = ""
    var garagem: String = ""
    var status: Boolean = false

    init {
        this.id = reference.push().key!!
    }
}




