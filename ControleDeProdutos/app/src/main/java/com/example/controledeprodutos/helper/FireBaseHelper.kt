package com.example.controledeprodutos.helper

import com.google.firebase.auth.FirebaseAuth

class FireBaseHelper {
    private lateinit var auth: FirebaseAuth

    fun getAuth(): FirebaseAuth {
        if (auth == null) {
            auth = FirebaseAuth.getInstance()
        }
        return auth

    }

}