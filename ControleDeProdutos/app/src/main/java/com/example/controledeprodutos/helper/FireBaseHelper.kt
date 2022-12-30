package com.example.controledeprodutos.helper

import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FireBaseHelper {

    companion object {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        val dateBase: DatabaseReference = FirebaseDatabase.getInstance().reference

        fun getIdFirebase():String?{
            return auth.uid
        }
        fun isAuth(): Boolean {
            return auth.currentUser != null
        }
    }
//    init {
//        this
//    }
}