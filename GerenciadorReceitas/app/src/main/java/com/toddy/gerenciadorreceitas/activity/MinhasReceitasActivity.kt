package com.toddy.gerenciadorreceitas.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.toddy.gerenciadorreceitas.R
import com.toddy.gerenciadorreceitas.adapter.AdapterReceitas
import com.toddy.gerenciadorreceitas.models.Receita
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import com.tsuryo.swipeablerv.SwipeableRecyclerView

class MinhasReceitasActivity : AppCompatActivity(), AdapterReceitas.OnClick {
    private lateinit var ivVoltar: ImageView
    private lateinit var ivAdicionar: ImageView
    private lateinit var tituloToolbar: TextView
    private lateinit var tvInfo: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var ll_info: LinearLayout

    private lateinit var adapter: AdapterReceitas
    private lateinit var rv: SwipeableRecyclerView

    private var receitaList = mutableListOf<Receita>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_anuncios)

        initComponents()
        configClicks()
        configRV()
    }

    override fun onStart() {
        super.onStart()
        recuperarAnuncios()
    }

    override fun onResume() {
        super.onResume()
        receitaList.clear()
        recuperarAnuncios()
    }

    private fun initComponents() {
        ivVoltar = findViewById(R.id.iv_back)
        ll_info = findViewById(R.id.ll_info)
        tvInfo = findViewById(R.id.tv_info)
        progressBar = findViewById(R.id.progress_bar)
        rv = findViewById(R.id.rv)
        ivAdicionar = findViewById(R.id.iv_add)
        tituloToolbar = findViewById(R.id.toolbar_txt)
        tituloToolbar.text = "Minhas Receitas"
    }

    private fun configClicks() {
        ivVoltar.setOnClickListener {
            finish()
        }
        ivAdicionar.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }
    }

    private fun configRV() {
        rv.layoutManager = LinearLayoutManager(this)
        rv.setHasFixedSize(true)

        adapter = AdapterReceitas(receitaList, this)

        rv.adapter = adapter

        rv.setListener(object : SwipeLeftRightCallback.Listener {
            override fun onSwipedLeft(position: Int) {
                showDialogDelete(position)
            }

            override fun onSwipedRight(position: Int) {
                showDialogDelete(position)
            }

        })
    }

    private fun showDialogDelete(pos: Int) {
        AlertDialog.Builder(this)
            .setTitle("Apagar Receita")
            .setMessage("Deseja apagar essa receita?")
            .setNegativeButton("Não") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                adapter.notifyDataSetChanged()
            }
            .setPositiveButton("Sim") { _: DialogInterface, _: Int ->
                receitaList[pos].deletarReceita()
                adapter.notifyItemRemoved(pos)
            }.show()
    }

    override fun onClickListener(receita: Receita) {
        val intent = Intent(this, RecipeItemActivity::class.java)
        intent.putExtra("receita", receita)
        startActivity(intent)

    }

    private fun recuperarAnuncios() {
        val reference: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("receitas")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)

        ll_info.visibility = View.VISIBLE



        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val children = snapshot.children
                    receitaList.clear()
                    children.forEach {
                        it.getValue(Receita::class.java).let { receita ->
                            receitaList.add(receita!!)
                        }
                    }
                    ll_info.visibility = View.GONE
                } else {
                    tvInfo.text = "Nenhuma receita cadastrada"
                }
                receitaList.reverse()
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}