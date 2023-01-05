package com.toddy.casaportemporada.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class Anuncio(reference: DatabaseReference = FirebaseDatabase.getInstance().reference) :
    java.io.Serializable {
    var id: String = ""
    var idUser: String = ""
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

        FirebaseDatabase.getInstance().reference
            .child("anuncios_publicos")
            .child(this.id)
            .setValue(this)
    }

    fun deletarAnuncio() {
        FirebaseDatabase.getInstance().reference
            .child("anuncios")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(this.id)
            .removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    FirebaseStorage.getInstance().reference
                        .child("imagens")
                        .child("anuncios")
                        .child(this.id + "jpg")
                        .delete()
                }
            }
    }

}




