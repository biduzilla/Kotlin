package com.toddy.olxclone.model

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.toddy.olxclone.activitys.FormActivity
import com.toddy.olxclone.activitys.MainActivity

class Anuncio(reference: DatabaseReference = FirebaseDatabase.getInstance().reference) :
    java.io.Serializable {
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

    fun salvar(activity: Activity, novoAnuncios: Boolean) {
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

            dataAnuncioMeus.setValue(ServerValue.TIMESTAMP).addOnCompleteListener {
                activity.finish()
                val intent = Intent(activity, MainActivity::class.java)
                intent.putExtra("id", 2)
                activity.startActivity(intent)
            }
        } else {
            activity.finish()
        }
    }

    fun remover(anuncio: Anuncio) {
        val anuncioPubRef: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("anuncios_publicos")
            .child(this.id!!)
        anuncioPubRef.removeValue()

        val anuncioMeusRef: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("meus_anuncios")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .child(this.id!!)
        anuncioMeusRef.removeValue()

        imagens.forEachIndexed { index, imagem ->
            val storage: StorageReference = FirebaseStorage.getInstance().reference
                .child("imagens")
                .child("anuncios")
                .child(id!!)
                .child("imagem ${index}.jpeg")
            storage.delete()
        }

    }
}