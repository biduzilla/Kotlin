package com.example.controledeprodutos.models

import com.example.controledeprodutos.helper.FireBaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProdutoEntity(databaseReference: DatabaseReference = FireBaseHelper.dateBase) :
    java.io.Serializable {
    var id: String = ""
    var nome: String = ""
    var estoque: Int = 0
    var valor: Double = 0.0
    var urlImagem: String = ""


    init {
        this.id = databaseReference.push().key!!
    }

    fun salvarProduto() {
        val reference: DatabaseReference =
            FireBaseHelper.dateBase.database.reference.child("produtos")
                .child(FireBaseHelper.getIdFirebase()!!).child(this.id)
        reference.setValue(this)
    }

    fun deletarProduto() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance()
            .reference
            .child("produtos")
            .child(
                FirebaseAuth
                    .getInstance()
                    .currentUser!!
                    .uid
            )
            .child(this.id)
        reference.removeValue()

        val storageReference: StorageReference =
            FirebaseStorage.getInstance().reference
                .child("imagens")
                .child("produtos")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(this.id+".jpeg")

        storageReference.delete()

    }

}