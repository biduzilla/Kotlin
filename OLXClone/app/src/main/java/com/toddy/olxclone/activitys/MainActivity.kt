package com.toddy.olxclone.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.toddy.olxclone.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottonNavView: BottomNavigationView = findViewById(R.id.navigation_view)
        val navController:NavController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottonNavView,navController)

        val id:Int = intent.getIntExtra("id",0)
        if (id == 2){
            bottonNavView.selectedItemId = R.id.menu_meus_anuncios
        }
    }
}