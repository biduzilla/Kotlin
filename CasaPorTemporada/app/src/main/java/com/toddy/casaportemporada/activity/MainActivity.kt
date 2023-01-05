package com.toddy.casaportemporada.activity

import android.app.ProgressDialog.THEME_DEVICE_DEFAULT_DARK
import android.app.ProgressDialog.show
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.activity.auth.LoginActivity
import com.toddy.casaportemporada.adapter.AdapterAnuncios
import com.toddy.casaportemporada.model.Anuncio
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), AdapterAnuncios.OnClick {
    private lateinit var adapter: AdapterAnuncios
    private lateinit var ibMore: ImageButton
    private lateinit var tvInfo: TextView
    private lateinit var tituloToolbar: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private var anunciosList = mutableListOf<Anuncio>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        configRv()
        recuperaAnuncios()
        configClicks()
    }

    override fun onResume() {
        super.onResume()
        recuperaAnuncios()
    }

    private fun configRv() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = AdapterAnuncios(anunciosList, this)
        recyclerView.adapter = adapter
    }

    private fun recuperaAnuncios() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("anuncios_publicos")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    anunciosList.clear()
                    children.forEach {
                        it.getValue(Anuncio::class.java).let { anuncio ->
                            anunciosList.add(anuncio!!)

                        }
                    }
                } else {
                    tvInfo.text = "Nenhum Anúncio Cadastrado"
                }

                progressBar.visibility = View.GONE
                anunciosList.reverse()
                adapter.notifyDataSetChanged()
                tvInfo.text = ""

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun initComponents() {
        ibMore = findViewById(R.id.ib_menu)
        progressBar = findViewById(R.id.progress_bar)
        tvInfo = findViewById(R.id.tv_info)
        recyclerView = findViewById(R.id.rv_anuncios)
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

    override fun onClickListener(anuncio: Anuncio) {
        val intent = Intent(this, DetalheAnuncioActivity::class.java)
        intent.putExtra("anuncio", anuncio)
        startActivity(intent)
    }
}
