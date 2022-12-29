package com.example.controledeprodutos.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.controledeprodutos.R

class LoginActivity : AppCompatActivity() {
    private var tvCriarConta: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initComponets()
        configClicks()
    }

    private fun configClicks(){
        tvCriarConta!!.setOnClickListener{
            startActivity(Intent(this,CriarContaActivity::class.java))
        }
    }

    private fun initComponets(){
        tvCriarConta = findViewById(R.id.tv_criar_conta)
    }
}