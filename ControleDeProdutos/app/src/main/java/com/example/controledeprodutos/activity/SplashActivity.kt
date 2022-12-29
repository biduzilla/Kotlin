package com.example.controledeprodutos.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.controledeprodutos.R
import com.example.controledeprodutos.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        loadComponents()
        Handler(Looper.getMainLooper()).postDelayed(this::verficarLogin, 3000)


    }

    private fun loadComponents() {
        auth = FirebaseAuth.getInstance()
    }

    private fun verficarLogin() {
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}