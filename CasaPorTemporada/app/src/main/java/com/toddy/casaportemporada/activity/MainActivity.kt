package com.toddy.casaportemporada.activity

import android.app.ProgressDialog.THEME_DEVICE_DEFAULT_DARK
import android.app.ProgressDialog.show
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.activity.auth.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var ibMore: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        configClicks()
    }

    private fun initComponents() {
        ibMore = findViewById(R.id.ib_menu)
    }

    private fun configClicks() {
        ibMore.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, ibMore)
            popupMenu.menuInflater.inflate(R.menu.menu_home, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {

                when (it.itemId) {
                    R.id.menu_filtrar -> {
                        startActivity(Intent(this, FiltrarAnunciosActivity::class.java))
                    }
                    R.id.menu_meus_anuncios -> {
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            startActivity(Intent(this, MeusAnunciosActivity::class.java))
                        } else {
                            showDialogLogin()
                        }

                    }
//                    R.id.menu_minha_conta -> {
//                        startActivity(Intent(this, MinhaContaActivity::class.java))
//                    }
                    else -> {
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            startActivity(Intent(this, MinhaContaActivity::class.java))
                        } else {
                            showDialogLogin()
                        }

                    }

                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    private fun showDialogLogin() {
        val build: AlertDialog.Builder = AlertDialog.Builder(this)
        build.setTitle("Autentificação")
        build.setCancelable(false)

        AlertDialog.Builder(this).setTitle("Autentificação").setCancelable(false)
            .setMessage("Você não está logado, você quer logar novamente?")
            .setNegativeButton("Não") { dialog: DialogInterface, which: Int -> dialog.dismiss() }
            .setPositiveButton("Sim") { dialog: DialogInterface, which: Int ->
                startActivity(Intent(this, LoginActivity::class.java))

            }.show()
    }
}
