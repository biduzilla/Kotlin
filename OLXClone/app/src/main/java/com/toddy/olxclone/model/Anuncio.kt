package com.toddy.olxclone.model

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Anuncio(reference: DatabaseReference = FirebaseDatabase.getInstance().reference) {
    var id: String? = ""
    var titulo: String? = ""
    var descricao: String? = ""
    var valor: Double? = 0.0
    var categoria: String? = ""
    var local: Local? = null
    var dataPublicacao: Long? = 0
    var idUser: String? = ""
    var imagens = mutableListOf<String>()



    init {
        this.id = reference.push().key!!
    }

    fun salvar(novoAnuncios: Boolean) {
        val anuncioPubRef: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("anuncios_publicos")
            .child(this.id!!)

        val anuncioMeusRef: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("meus_anuncios")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(this.id!!)

        anuncioPubRef.setValue(this)

        anuncioMeusRef.setValue(this)

        if (novoAnuncios) {
            val dataAnuncioPublico: DatabaseReference = anuncioPubRef
                .child("dataPublicacao")

            dataAnuncioPublico.setValue(ServerValue.TIMESTAMP)

            val dataAnuncioMeus: DatabaseReference = anuncioMeusRef
                .child("dataPublicacao")

            dataAnuncioMeus.setValue(ServerValue.TIMESTAMP)
        }
    }
}