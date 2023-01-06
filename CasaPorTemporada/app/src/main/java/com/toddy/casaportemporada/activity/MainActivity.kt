package com.toddy.casaportemporada.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.toddy.casaportemporada.R
import com.toddy.casaportemporada.activity.auth.LoginActivity
import com.toddy.casaportemporada.adapter.AdapterAnuncios
import com.toddy.casaportemporada.model.Anuncio
import com.toddy.casaportemporada.model.Filtro


class MainActivity : AppCompatActivity(), AdapterAnuncios.OnClick {
    private lateinit var adapter: AdapterAnuncios
    private lateinit var ibMore: ImageButton
    private lateinit var tvInfo: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private var anunciosList = mutableListOf<Anuncio>()
    private var filtro = Filtro()

    private var resultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startResult()
        initComponents()
        configRv()
        recuperaAnuncios()
        configClicks()
    }

    private fun startResult() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = it.data
                    filtro = data!!.getSerializableExtra("filtro") as Filtro
                    if (filtro.qtdQuarto > 0 || filtro.qtdBanheiro > 0 || filtro.qtdGaragem > 0) {
                        recuperaAnunciosFiltrados()
                    }
                    else {
                        recuperaAnuncios()
                    }
                }

            }
    }

    private fun recuperaAnunciosFiltrados() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("anuncios_publicos")

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    anunciosList.clear()
                    val children = snapshot.children
                    children.forEach {
                        it.getValue(Anuncio::class.java).let { anuncio ->

                            val quarto: Int = anuncio!!.quarto.toInt()
                            val banheiro: Int = anuncio.banheiro.toInt()
                            val garagem: Int = anuncio.garagem.toInt()

                            if (quarto >= filtro.qtdQuarto &&
                                banheiro >= filtro.qtdBanheiro &&
                                garagem >= filtro.qtdGaragem
                            ) {
                                anunciosList.add(anuncio)
                            }
                        }
                    }
                }
                if (anunciosList.size == 0) {
                    tvInfo.text = "Nenhum Anúncio Encontrado"
                } else {
                    tvInfo.text = ""
                }
                progressBar.visibility = View.GONE
                anunciosList.reverse()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
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

    private fun configRv() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = AdapterAnuncios(anunciosList, this)
        recyclerView.adapter = adapter
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
                        val intent = Intent(this, FiltrarAnunciosActivity::class.java)
                        intent.putExtra("filtro", filtro)
                        resultLauncher!!.launch(intent)
                    }
                    R.id.menu_meus_anuncios -> {
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            startActivity(Intent(this, MeusAnunciosActivity::class.java))
                        } else {
                            showDialogLogin()
                        }
                    }
                    R.id.menu_minha_conta -> {
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            startActivity(Intent(this, MinhaContaActivity::class.java))
                        } else {
                            showDialogLogin()
                        }
                    }
                    else -> {
                        FirebaseAuth.getInstance().signOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
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
