package com.toddy.olxclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottonNavView: BottomNavigationView = findViewById(R.id.navigation_view)
        val navController:NavController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupWithNavController(bottonNavView,navController)
    }
}