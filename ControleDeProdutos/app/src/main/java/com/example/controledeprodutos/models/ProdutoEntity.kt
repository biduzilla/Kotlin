package com.example.controledeprodutos.models

import com.example.controledeprodutos.helper.FireBaseHelper
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProdutoEntity(databaseReference: DatabaseReference = FireBaseHelper.dateBase) : java.io.Serializable {
    var id: String = ""
    var nome: String = ""
    var estoque: Int = 0
    var valor: Double = 0.0

    init {
        this.id = databaseReference.push().key!!
    }

    fun salvarProduto() {
        val reference: DatabaseReference =
            FireBaseHelper.dateBase.database.reference.child("produtos")
                .child(FireBaseHelper.getIdFirebase()!!).child(this.id)
        reference.setValue(this)
    }

}